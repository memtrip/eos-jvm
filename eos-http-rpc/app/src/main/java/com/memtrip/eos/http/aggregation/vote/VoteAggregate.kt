package com.memtrip.eos.http.aggregation.vote

import com.memtrip.eos.abi.writer.compression.CompressionType
import com.memtrip.eos.core.block.BlockIdDetails
import com.memtrip.eos.core.crypto.EosPrivateKey
import com.memtrip.eos.core.crypto.signature.PrivateKeySigning
import com.memtrip.eos.http.aggregation.AggregateResponse
import com.memtrip.eos.http.aggregation.vote.actions.VoteArgs
import com.memtrip.eos.http.aggregation.vote.actions.VoteBody

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

class VoteAggregate(
    private val chainApi: ChainApi
) {

    data class Args(
        val voter: String,
        val proxy: String,
        val producers: List<String>,
        val authorizingAccountName: String,
        val authorizingPrivateKey: EosPrivateKey,
        val expirationDate: LocalDateTime
    )

    fun vote(args: Args): Single<AggregateResponse<TransactionCommitted>> {
        return chainApi.getInfo().flatMap { info ->
            if (info.isSuccessful) {

                val transaction = vote(
                    args,
                    BlockIdDetails(info.body()!!.head_block_id),
                    voteBin(args))

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

    private fun vote(
        args: Args,
        blockIdDetails: BlockIdDetails,
        voteBin: String
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
                "eosio",
                "voteproducer",
                Arrays.asList(TransactionAuthorization(
                    args.authorizingAccountName,
                    "active")),
                voteBin
            )),
            emptyList(),
            emptyList(),
            emptyList())
    }
}