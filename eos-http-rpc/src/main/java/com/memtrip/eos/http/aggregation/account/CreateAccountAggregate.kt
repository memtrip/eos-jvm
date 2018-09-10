package com.memtrip.eos.http.aggregation.account

import com.memtrip.eos.abi.writer.compression.CompressionType
import com.memtrip.eos.core.crypto.EosPublicKey
import com.memtrip.eos.http.aggregation.AggregateContext

import com.memtrip.eos.http.aggregation.AggregateResponse
import com.memtrip.eos.http.aggregation.AggregateTransaction
import com.memtrip.eos.http.aggregation.account.actions.buyram.BuyRamArgs
import com.memtrip.eos.http.aggregation.account.actions.buyram.BuyRamBody
import com.memtrip.eos.http.aggregation.account.actions.delegatebw.DelegateBandwidthArgs
import com.memtrip.eos.http.aggregation.account.actions.delegatebw.DelegateBandwidthBody
import com.memtrip.eos.http.aggregation.account.actions.newaccount.NewAccountArgs
import com.memtrip.eos.http.aggregation.account.actions.newaccount.NewAccountBody

import com.memtrip.eos.http.rpc.ChainApi
import com.memtrip.eos.http.rpc.model.account.response.AccountKey
import com.memtrip.eos.http.rpc.model.account.response.AccountRequiredAuth
import com.memtrip.eos.http.rpc.model.transaction.TransactionAuthorization
import com.memtrip.eos.http.rpc.model.transaction.request.Action
import com.memtrip.eos.http.rpc.model.transaction.response.TransactionCommitted
import com.memtrip.eosio.abi.binary.gen.AbiBinaryGen
import io.reactivex.Single
import java.util.Arrays

class CreateAccountAggregate(chainApi: ChainApi) : AggregateTransaction(chainApi) {

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
        aggregateContext: AggregateContext
    ): Single<AggregateResponse<TransactionCommitted>> {

        return push(
            aggregateContext.expirationDate,
            Arrays.asList(
                Action(
                    "eosio",
                    "newaccount",
                    Arrays.asList(TransactionAuthorization(
                        aggregateContext.authorizingAccountName,
                        "active")
                    ),
                    newAccountAbi(args, aggregateContext)
                ),
                Action(
                    "eosio",
                    "buyram",
                    Arrays.asList(TransactionAuthorization(
                        aggregateContext.authorizingAccountName,
                        "active")
                    ),
                    buyRamAbi(args, aggregateContext)
                ),
                Action(
                    "eosio",
                    "delegatebw",
                    Arrays.asList(TransactionAuthorization(
                        aggregateContext.authorizingAccountName,
                        "active")
                    ),
                    delegateRamAbi(args, aggregateContext)
                )
            ),
            aggregateContext.authorizingPrivateKey
        )
    }

    private fun newAccountAbi(args: Args, aggregateContext: AggregateContext): String {
        return AbiBinaryGen(CompressionType.NONE).squishNewAccountBody(
            NewAccountBody(
                NewAccountArgs(
                    aggregateContext.authorizingAccountName,
                    args.newAccountName,
                    AccountRequiredAuth(
                        1,
                        Arrays.asList(
                            AccountKey(
                                args.ownerPublicKey.toString(),
                                1)
                        ),
                        emptyList(),
                        emptyList()
                    ),
                    AccountRequiredAuth(
                        1,
                        Arrays.asList(
                            AccountKey(
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

    private fun buyRamAbi(args: Args, aggregateContext: AggregateContext): String {
        return AbiBinaryGen(CompressionType.NONE).squishBuyRamBody(
            BuyRamBody(
                BuyRamArgs(
                    aggregateContext.authorizingAccountName,
                    args.newAccountName,
                    args.quantity.ram)
            )
        ).toHex()
    }

    private fun delegateRamAbi(args: Args, aggregateContext: AggregateContext): String {
        return AbiBinaryGen(CompressionType.NONE).squishDelegateBandwidthBody(
            DelegateBandwidthBody(
                "eosio",
                "delegatebw",
                DelegateBandwidthArgs(
                    aggregateContext.authorizingAccountName,
                    args.newAccountName,
                    args.quantity.net,
                    args.quantity.cpu,
                    if (args.transfer) 1 else 0
                )
            )
        ).toHex()
    }
}