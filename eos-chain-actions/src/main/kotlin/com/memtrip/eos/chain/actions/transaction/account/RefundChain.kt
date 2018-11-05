package com.memtrip.eos.chain.actions.transaction.account

import com.memtrip.eos.abi.writer.compression.CompressionType
import com.memtrip.eos.chain.actions.ChainResponse
import com.memtrip.eos.chain.actions.transaction.AbiBinaryGenTransactionWriter
import com.memtrip.eos.chain.actions.transaction.ChainTransaction
import com.memtrip.eos.chain.actions.transaction.TransactionContext
import com.memtrip.eos.chain.actions.transaction.abi.ActionAbi
import com.memtrip.eos.chain.actions.transaction.abi.TransactionAuthorizationAbi
import com.memtrip.eos.chain.actions.transaction.account.actions.refund.RefundArgs
import com.memtrip.eos.chain.actions.transaction.account.actions.refund.RefundBody

import com.memtrip.eos.http.rpc.ChainApi
import com.memtrip.eos.http.rpc.model.transaction.response.TransactionCommitted
import io.reactivex.Single
import java.util.Arrays.asList

class RefundChain(chainApi: ChainApi) : ChainTransaction(chainApi) {

    fun refund(
        transactionContext: TransactionContext
    ): Single<ChainResponse<TransactionCommitted>> {

        return push(
            transactionContext.expirationDate,
            asList(ActionAbi(
                "eosio",
                "refund",
                asList(TransactionAuthorizationAbi(
                    transactionContext.authorizingAccountName,
                    "active")),
                refundAbi(transactionContext)
            )),
            transactionContext.authorizingPrivateKey
        )
    }

    private fun refundAbi(transactionContext: TransactionContext): String {
        return AbiBinaryGenTransactionWriter(CompressionType.NONE).squishRefundBody(
            RefundBody(RefundArgs(transactionContext.authorizingAccountName))
        ).toHex()
    }
}