package com.memtrip.eos.http.aggregation

import com.memtrip.eos.abi.writer.compression.CompressionType
import com.memtrip.eos.core.block.BlockIdDetails
import com.memtrip.eos.core.crypto.EosPrivateKey
import com.memtrip.eos.core.crypto.signature.PrivateKeySigning
import com.memtrip.eos.http.rpc.ChainApi
import com.memtrip.eos.http.rpc.model.signing.PushTransaction
import com.memtrip.eos.http.rpc.model.transaction.request.Action
import com.memtrip.eos.http.rpc.model.transaction.request.SignedTransaction
import com.memtrip.eos.http.rpc.model.transaction.request.Transaction
import com.memtrip.eos.http.rpc.model.transaction.response.TransactionCommitted
import com.memtrip.eosio.abi.binary.gen.AbiBinaryGen
import io.reactivex.Single
import retrofit2.Response
import java.util.Date
import java.util.Arrays.asList

abstract class AggregateTransaction(
    private val chainApi: ChainApi
) {

    internal fun push(
        expirationDate: Date,
        actions: List<Action>,
        authorizingPrivateKey: EosPrivateKey
    ): Single<AggregateResponse<TransactionCommitted>> {
        return chainApi.getInfo().flatMap { info ->
            if (info.isSuccessful) {
                val transaction = transaction(
                    expirationDate,
                    BlockIdDetails(info.body()!!.head_block_id),
                    actions)

                val signature = PrivateKeySigning().sign(
                    AbiBinaryGen(CompressionType.NONE).squishSignedTransaction(
                        SignedTransaction(
                            info.body()!!.chain_id,
                            transaction,
                            emptyList()
                        )
                    ).toBytes(), authorizingPrivateKey)

                chainApi.pushTransaction(
                    PushTransaction(
                        asList(signature),
                        "none",
                        "",
                        AbiBinaryGen(CompressionType.NONE).squishTransaction(transaction).toHex()))
            } else {
                Single.just(Response.error(info.code(), info.errorBody()!!))
            }
        }.map {
            AggregateResponse(
                it.isSuccessful,
                it.code(),
                it.body(),
                it.errorBody()?.string())
        }
    }

    private fun transaction(
        expirationDate: Date,
        blockIdDetails: BlockIdDetails,
        actions: List<Action>
    ): Transaction {

        return Transaction(
            expirationDate,
            blockIdDetails.blockNum,
            blockIdDetails.blockPrefix,
            0,
            0,
            0,
            emptyList(),
            actions,
            emptyList(),
            emptyList(),
            emptyList())
    }
}