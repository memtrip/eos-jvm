package com.memtrip.eos.http.aggregation.transfer

import com.memtrip.eos.abi.writer.compression.CompressionType
import com.memtrip.eos.core.block.BlockIdDetails
import com.memtrip.eos.core.crypto.EosPrivateKey
import com.memtrip.eos.core.crypto.signature.PrivateKeySigning
import com.memtrip.eos.http.aggregation.AggregateContext
import com.memtrip.eos.http.aggregation.AggregateResponse
import com.memtrip.eos.http.aggregation.AggregateTransaction

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
import java.util.Date
import retrofit2.Response
import java.util.Arrays

class TransferAggregate(chainApi: ChainApi) : AggregateTransaction(chainApi) {

    data class Args(
        val fromAccount: String,
        val toAccount: String,
        val quantity: String,
        val memo: String
    )

    fun transfer(
        args: Args,
        aggregateContext: AggregateContext
    ): Single<AggregateResponse<TransactionCommitted>> {

        return push(
            aggregateContext.expirationDate,
            Arrays.asList(Action(
                "eosio.token",
                "transfer",
                Arrays.asList(TransactionAuthorization(
                    aggregateContext.authorizingAccountName,
                    "active")),
                transferBin(args)
            )),
            aggregateContext.authorizingPrivateKey
        )
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
}