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
package com.memtrip.eos.chain.actions.transaction.account

import com.memtrip.eos.abi.writer.compression.CompressionType
import com.memtrip.eos.chain.actions.ChainResponse
import com.memtrip.eos.chain.actions.transaction.AbiBinaryGenTransactionWriter
import com.memtrip.eos.chain.actions.transaction.ChainTransaction
import com.memtrip.eos.chain.actions.transaction.TransactionContext
import com.memtrip.eos.chain.actions.transaction.abi.ActionAbi
import com.memtrip.eos.chain.actions.transaction.abi.TransactionAuthorizationAbi
import com.memtrip.eos.chain.actions.transaction.account.actions.buyram.BuyRamArgs
import com.memtrip.eos.chain.actions.transaction.account.actions.buyram.BuyRamBody
import com.memtrip.eos.chain.actions.transaction.account.actions.delegatebw.DelegateBandwidthArgs
import com.memtrip.eos.chain.actions.transaction.account.actions.delegatebw.DelegateBandwidthBody
import com.memtrip.eos.chain.actions.transaction.account.actions.newaccount.AccountKeyAbi
import com.memtrip.eos.chain.actions.transaction.account.actions.newaccount.AccountRequiredAuthAbi
import com.memtrip.eos.chain.actions.transaction.account.actions.newaccount.NewAccountArgs
import com.memtrip.eos.chain.actions.transaction.account.actions.newaccount.NewAccountBody
import com.memtrip.eos.core.crypto.EosPublicKey
import com.memtrip.eos.http.rpc.ChainApi
import com.memtrip.eos.http.rpc.model.transaction.response.TransactionCommitted
import io.reactivex.Single
import java.util.Arrays.asList

class CreateAccountChain(chainApi: ChainApi) : ChainTransaction(chainApi) {

    data class Args(
        val newAccountName: String,
        val quantity: Quantity,
        val ownerPublicKey: EosPublicKey,
        val activePublicKey: EosPublicKey,
        val transfer: Boolean
    ) {
        data class Quantity(
            val ram: String,
            val net: String,
            val cpu: String
        )
    }

    fun createAccount(
        args: Args,
        transactionContext: TransactionContext,
        extraActionAbi: List<ActionAbi> = emptyList()
    ): Single<ChainResponse<TransactionCommitted>> {

        return push(
            transactionContext.expirationDate,
            with (ArrayList<ActionAbi>(asList(
                ActionAbi(
                    "eosio",
                    "newaccount",
                    asList(TransactionAuthorizationAbi(
                        transactionContext.authorizingAccountName,
                        "active")
                    ),
                    newAccountAbi(args, transactionContext)
                ),
                ActionAbi(
                    "eosio",
                    "buyram",
                    asList(TransactionAuthorizationAbi(
                        transactionContext.authorizingAccountName,
                        "active")
                    ),
                    buyRamAbi(args, transactionContext)
                ),
                ActionAbi(
                    "eosio",
                    "delegatebw",
                    asList(TransactionAuthorizationAbi(
                        transactionContext.authorizingAccountName,
                        "active")
                    ),
                    delegateRamAbi(args, transactionContext)
                )
            ))) {
                addAll(extraActionAbi)
                this
            },
            transactionContext.authorizingPrivateKey
        )
    }

    private fun newAccountAbi(args: Args, transactionContext: TransactionContext): String {
        return AbiBinaryGenTransactionWriter(CompressionType.NONE).squishNewAccountBody(
            NewAccountBody(
                NewAccountArgs(
                    transactionContext.authorizingAccountName,
                    args.newAccountName,
                    AccountRequiredAuthAbi(
                        1,
                        asList(
                            AccountKeyAbi(
                                args.ownerPublicKey.toString(),
                                1)
                        ),
                        emptyList(),
                        emptyList()
                    ),
                    AccountRequiredAuthAbi(
                        1,
                        asList(
                            AccountKeyAbi(
                                args.activePublicKey.toString(),
                                1)
                        ),
                        emptyList(),
                        emptyList()
                    )
                )
            )
        ).toHex()
    }

    private fun buyRamAbi(args: Args, transactionContext: TransactionContext): String {
        return AbiBinaryGenTransactionWriter(CompressionType.NONE).squishBuyRamBody(
            BuyRamBody(
                BuyRamArgs(
                    transactionContext.authorizingAccountName,
                    args.newAccountName,
                    args.quantity.ram)
            )
        ).toHex()
    }

    private fun delegateRamAbi(args: Args, transactionContext: TransactionContext): String {
        return AbiBinaryGenTransactionWriter(CompressionType.NONE).squishDelegateBandwidthBody(
            DelegateBandwidthBody(
                "eosio",
                "delegatebw",
                DelegateBandwidthArgs(
                    transactionContext.authorizingAccountName,
                    args.newAccountName,
                    args.quantity.net,
                    args.quantity.cpu,
                    if (args.transfer) 1 else 0
                )
            )
        ).toHex()
    }
}