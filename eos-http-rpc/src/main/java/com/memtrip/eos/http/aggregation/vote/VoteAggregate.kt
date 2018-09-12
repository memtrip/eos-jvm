package com.memtrip.eos.http.aggregation.vote

import com.memtrip.eos.abi.writer.compression.CompressionType
import com.memtrip.eos.http.aggregation.AggregateContext
import com.memtrip.eos.http.aggregation.AggregateResponse
import com.memtrip.eos.http.aggregation.AggregateTransaction
import com.memtrip.eos.http.aggregation.vote.actions.VoteArgs
import com.memtrip.eos.http.aggregation.vote.actions.VoteBody
import com.memtrip.eos.http.rpc.ChainApi
import com.memtrip.eos.http.rpc.model.transaction.TransactionAuthorization
import com.memtrip.eos.http.rpc.model.transaction.request.Action
import com.memtrip.eos.http.rpc.model.transaction.response.TransactionCommitted
import com.memtrip.eosio.abi.binary.gen.AbiBinaryGen
import io.reactivex.Single
import java.util.Arrays.asList

class VoteAggregate(chainApi: ChainApi) : AggregateTransaction(chainApi) {

    data class Args(
        val voter: String,
        val proxy: String,
        val producers: List<String>
    )

    fun vote(
        args: Args,
        aggregateContext: AggregateContext
    ): Single<AggregateResponse<TransactionCommitted>> {

        return push(
            aggregateContext.expirationDate,
            asList(Action(
                "eosio",
                "voteproducer",
                asList(TransactionAuthorization(
                    aggregateContext.authorizingAccountName,
                    "active")),
                voteBin(args)
            )),
            aggregateContext.authorizingPrivateKey
        )
    }

    private fun voteBin(args: Args): String {
        return AbiBinaryGen(CompressionType.NONE).squishVoteBody(
            VoteBody(
                "eosio",
                "voteproducer",
                VoteArgs(
                    args.voter,
                    args.proxy,
                    args.producers)
            )
        ).toHex()
    }
}