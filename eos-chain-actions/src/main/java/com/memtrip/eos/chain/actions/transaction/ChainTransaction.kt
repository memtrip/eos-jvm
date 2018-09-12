package com.memtrip.eos.chain.actions.transaction

import com.memtrip.eos.abi.writer.compression.CompressionType
import com.memtrip.eos.chain.actions.ChainResponse
import com.memtrip.eos.chain.actions.transaction.abi.ActionAbi
import com.memtrip.eos.chain.actions.transaction.abi.SignedTransactionAbi
import com.memtrip.eos.chain.actions.transaction.abi.TransactionAbi
import com.memtrip.eos.core.block.BlockIdDetails
import com.memtrip.eos.core.crypto.EosPrivateKey
import com.memtrip.eos.core.crypto.signature.PrivateKeySigning
import com.memtrip.eos.http.rpc.ChainApi
import com.memtrip.eos.http.rpc.model.signing.PushTransaction
import com.memtrip.eos.http.rpc.model.transaction.response.TransactionCommitted
import com.memtrip.eosio.abi.binary.gen.AbiBinaryGen
import io.reactivex.Single
import retrofit2.Response
import java.util.*
import java.util.Arrays.asList

abstract class ChainTransaction(
    private val chainApi: ChainApi
) {

    internal fun push(
        expirationDate: Date,
        actions: List<ActionAbi>,
        authorizingPrivateKey: EosPrivateKey
    ): Single<ChainResponse<TransactionCommitted>> {
        return chainApi.getInfo().flatMap { info ->
            if (info.isSuccessful) {
                val transaction = transaction(
                    expirationDate,
                    BlockIdDetails(info.body()!!.head_block_id),
                    actions)

                val signature = PrivateKeySigning().sign(
                    AbiBinaryGen(CompressionType.NONE).squishSignedTransactionAbi(
                        SignedTransactionAbi(
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
                        AbiBinaryGen(CompressionType.NONE).squishTransactionAbi(transaction).toHex()))
            } else {
                Single.just(Response.error(info.code(), info.errorBody()!!))
            }
        }.map {
            ChainResponse(
                it.isSuccessful,
                it.code(),
                it.body(),
                it.errorBody()?.string())
        }
    }

    private fun transaction(
        expirationDate: Date,
        blockIdDetails: BlockIdDetails,
        actions: List<ActionAbi>
    ): TransactionAbi {

        return TransactionAbi(
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