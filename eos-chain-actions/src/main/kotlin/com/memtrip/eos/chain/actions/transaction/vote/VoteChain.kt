package com.memtrip.eos.chain.actions.transaction.vote

import com.memtrip.eos.abi.writer.compression.CompressionType
import com.memtrip.eos.chain.actions.ChainResponse
import com.memtrip.eos.chain.actions.transaction.AbiBinaryGenTransactionWriter
import com.memtrip.eos.chain.actions.transaction.ChainTransaction
import com.memtrip.eos.chain.actions.transaction.TransactionContext
import com.memtrip.eos.chain.actions.transaction.abi.ActionAbi
import com.memtrip.eos.chain.actions.transaction.abi.TransactionAuthorizationAbi
import com.memtrip.eos.chain.actions.transaction.vote.actions.VoteArgs
import com.memtrip.eos.chain.actions.transaction.vote.actions.VoteBody
import com.memtrip.eos.http.rpc.ChainApi
import com.memtrip.eos.http.rpc.model.transaction.response.TransactionCommitted

import io.reactivex.Single
import java.util.Arrays.asList

class VoteChain(chainApi: ChainApi) : ChainTransaction(chainApi) {

    data class Args(
        val voter: String,
        val proxy: String,
        val producers: List<String>
    )

    fun vote(
        args: Args,
        transactionContext: TransactionContext
    ): Single<ChainResponse<TransactionCommitted>> {

        return push(
            transactionContext.expirationDate,
            asList(ActionAbi(
                "eosio",
                "voteproducer",
                asList(TransactionAuthorizationAbi(
                    transactionContext.authorizingAccountName,
                    "active")),
                voteBin(args)
            )),
            transactionContext.authorizingPrivateKey
        )
    }

    private fun voteBin(args: Args): String {
        return AbiBinaryGenTransactionWriter(CompressionType.NONE).squishVoteBody(
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