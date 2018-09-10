package com.memtrip.eos.http.aggregation.account

import com.memtrip.eos.abi.writer.compression.CompressionType
import com.memtrip.eos.http.aggregation.AggregateContext
import com.memtrip.eos.http.aggregation.AggregateResponse
import com.memtrip.eos.http.aggregation.AggregateTransaction
import com.memtrip.eos.http.aggregation.account.actions.delegatebw.DelegateBandwidthArgs
import com.memtrip.eos.http.aggregation.account.actions.delegatebw.DelegateBandwidthBody

import com.memtrip.eos.http.rpc.ChainApi
import com.memtrip.eos.http.rpc.model.transaction.TransactionAuthorization
import com.memtrip.eos.http.rpc.model.transaction.request.Action
import com.memtrip.eos.http.rpc.model.transaction.response.TransactionCommitted
import com.memtrip.eosio.abi.binary.gen.AbiBinaryGen
import io.reactivex.Single
import java.util.*

class DelegateBandwidthAggregate(chainApi: ChainApi) : AggregateTransaction(chainApi) {

    data class Args(
        val from: String,
        val receiver: String,
        val netQuantity: String,
        val cpuQuantity: String,
        val transfer: Boolean
    )

    fun delegateBandwidth(
        args: Args,
        aggregateContext: AggregateContext
    ): Single<AggregateResponse<TransactionCommitted>> {

        return push(
            aggregateContext.expirationDate,
            Arrays.asList(Action(
                "eosio",
                "delegatebw",
                Arrays.asList(TransactionAuthorization(
                    aggregateContext.authorizingAccountName,
                    "active")),
                delegateBandwidthBin(args)
            )),
            aggregateContext.authorizingPrivateKey
        )
    }

    private fun delegateBandwidthBin(args: Args): String {
        return AbiBinaryGen(CompressionType.NONE).squishDelegateBandwidthBody(
            DelegateBandwidthBody(
                "eosio",
                "delegatebw",
                DelegateBandwidthArgs(
                    args.from,
                    args.receiver,
                    args.netQuantity,
                    args.cpuQuantity,
                    if (args.transfer) 1 else 0)
            )
        ).toHex()
    }
}