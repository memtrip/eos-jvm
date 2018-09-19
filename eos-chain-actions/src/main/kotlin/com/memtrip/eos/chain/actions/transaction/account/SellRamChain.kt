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
import com.memtrip.eos.http.rpc.ChainApi
import com.memtrip.eos.http.rpc.model.transaction.response.TransactionCommitted

import io.reactivex.Single
import java.util.Arrays.asList

class SellRamChain(chainApi: ChainApi) : ChainTransaction(chainApi) {

    data class Args(
        val receiver: String,
        val quantity: String
    )

    fun sellRam(
        args: Args,
        transactionContext: TransactionContext
    ): Single<ChainResponse<TransactionCommitted>> {

        return push(
            transactionContext.expirationDate,
            asList(ActionAbi(
                "eosio",
                "sellram",
                asList(TransactionAuthorizationAbi(
                    transactionContext.authorizingAccountName,
                    "active")),
                sellRamAbi(args, transactionContext)
            )),
            transactionContext.authorizingPrivateKey
        )
    }

    private fun sellRamAbi(args: Args, transactionContext: TransactionContext): String {
        return AbiBinaryGenTransactionWriter(CompressionType.NONE).squishBuyRamBody(
            BuyRamBody(
                BuyRamArgs(
                    transactionContext.authorizingAccountName,
                    args.receiver,
                    args.quantity)
            )
        ).toHex()
    }
}