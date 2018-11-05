/**
 * Copyright 2013-present memtrip LTD.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import java.util.asList

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
                VoteArgs(
                    args.voter,
                    args.proxy,
                    args.producers)
            )
        ).toHex()
    }
}