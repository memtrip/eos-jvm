package com.memtrip.eos.http.aggregation.transfer

import com.memtrip.eos.abi.writer.compression.CompressionType
import com.memtrip.eos.core.block.BlockIdDetails
import com.memtrip.eos.core.crypto.EosPrivateKey
import com.memtrip.eos.core.crypto.signature.PrivateKeySigning
import com.memtrip.eos.http.aggregation.AggregateResponse

import com.memtrip.eos.http.aggregation.transfer.actions.TransferArgs
import com.memtrip.eos.http.aggregation.transfer.actions.TransferBody
import com.memtrip.eos.http.rpc.ChainApi
import com.memtrip.eos.http.rpc.model.signing.PushTransaction
import com.memtrip.eos.http.rpc.model.transaction.TransactionAuthorization
import com.memtrip.eos.http.rpc.model.transaction.request.Action
import com.memtrip.eos.http.rpc.model.transaction.request.SignedTransaction
import com.memtrip.eos.http.rpc.model.transaction.request.Transaction
import com.memtrip.eos.http.rpc.model.transaction.response.TransactionCommitted
import com.memtrip.eosio.abi.binary.gen.AbiBinaryGen
import io.reactivex.Single
import org.threeten.bp.LocalDateTime
import retrofit2.Response
import java.util.Arrays

class TransferAggregate(
    private val chainApi: ChainApi
) {

    data class Args(
        val fromAccount: String,
        val toAccount: String,
        val quantity: String,
        val memo: String,
        val authorizingAccountName: String,
        val authorizingPrivateKey: EosPrivateKey,
        val expirationDate: LocalDateTime
    )

    fun transfer(args: Args): Single<AggregateResponse<TransactionCommitted>> {
        return chainApi.getInfo().flatMap { info ->
            if (info.isSuccessful) {

                val transaction = transaction(
                    args,
                    BlockIdDetails(info.body()!!.head_block_id),
                    transferBin(args))

                val signature = PrivateKeySigning().sign(
                    AbiBinaryGen(CompressionType.NONE).squishSignedTransaction(
                        SignedTransaction(
                            info.body()!!.chain_id,
                            transaction,
                            emptyList()
                        )
                    ).toBytes(), args.authorizingPrivateKey)

                chainApi.pushTransaction(
                    PushTransaction(
                        Arrays.asList(signature),
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

    private fun transferBin(args: Args): String {
        return AbiBinaryGen(CompressionType.NONE).squishTransferBody(
            TransferBody(
                "eosio.token",
                "transfer",
                TransferArgs(
                    args.fromAccount,
                    args.toAccount,
                    args.quantity,
                    args.memo)
            )
        ).toHex()
    }

    private fun transaction(
        args: Args,
        blockIdDetails: BlockIdDetails,
        transferBin: String
    ): Transaction {
        return Transaction(
            args.expirationDate,
            blockIdDetails.blockNum,
            blockIdDetails.blockPrefix,
            0,
            0,
            0,
            emptyList(),
            Arrays.asList(Action(
                "eosio.token",
                "transfer",
                Arrays.asList(TransactionAuthorization(
                    args.authorizingAccountName,
                    "active")),
                transferBin
            )),
            emptyList(),
            emptyList(),
            emptyList())
    }
}