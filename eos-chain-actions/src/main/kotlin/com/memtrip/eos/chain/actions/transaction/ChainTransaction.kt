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
import io.reactivex.Single
import retrofit2.Response
import java.util.Date
import java.util.Arrays.asList

abstract class ChainTransaction(
    private val chainApi: ChainApi
) {

    fun push(
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
                    AbiBinaryGenTransactionWriter(CompressionType.NONE).squishSignedTransactionAbi(
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
                        AbiBinaryGenTransactionWriter(CompressionType.NONE).squishTransactionAbi(transaction).toHex()))
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