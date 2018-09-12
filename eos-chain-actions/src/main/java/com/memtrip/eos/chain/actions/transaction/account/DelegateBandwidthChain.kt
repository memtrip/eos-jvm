package com.memtrip.eos.chain.actions.transaction.account

import com.memtrip.eos.abi.writer.compression.CompressionType
import com.memtrip.eos.chain.actions.ChainResponse
import com.memtrip.eos.chain.actions.transaction.ChainTransaction
import com.memtrip.eos.chain.actions.transaction.TransactionContext
import com.memtrip.eos.chain.actions.transaction.abi.ActionAbi
import com.memtrip.eos.chain.actions.transaction.abi.TransactionAuthorizationAbi
import com.memtrip.eos.chain.actions.transaction.account.actions.delegatebw.DelegateBandwidthArgs
import com.memtrip.eos.chain.actions.transaction.account.actions.delegatebw.DelegateBandwidthBody
import com.memtrip.eos.http.rpc.ChainApi
import com.memtrip.eos.http.rpc.model.transaction.response.TransactionCommitted
import com.memtrip.eosio.abi.binary.gen.AbiBinaryGen
import io.reactivex.Single
import java.util.Arrays.asList

class DelegateBandwidthChain(chainApi: ChainApi) : ChainTransaction(chainApi) {

    data class Args(
        val from: String,
        val receiver: String,
        val netQuantity: String,
        val cpuQuantity: String,
        val transfer: Boolean
    )

    fun delegateBandwidth(
        args: Args,
        transactionContext: TransactionContext
    ): Single<ChainResponse<TransactionCommitted>> {

        return push(
            transactionContext.expirationDate,
            asList(ActionAbi(
                "eosio",
                "delegatebw",
                asList(TransactionAuthorizationAbi(
                    transactionContext.authorizingAccountName,
                    "active")),
                delegateBandwidthBin(args)
            )),
            transactionContext.authorizingPrivateKey
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