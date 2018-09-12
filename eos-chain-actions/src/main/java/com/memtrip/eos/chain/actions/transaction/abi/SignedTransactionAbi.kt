package com.memtrip.eos.chain.actions.transaction.abi

import com.memtrip.eos.abi.writer.Abi
import com.memtrip.eos.abi.writer.ChainIdCompress
import com.memtrip.eos.abi.writer.ChildCompress
import com.memtrip.eos.abi.writer.HexCollectionCompress

@Abi
data class SignedTransactionAbi(
    val chainId: String,
    val transaction: TransactionAbi,
    val context_free_data: List<String>
) {
    val getChainId: String
        @ChainIdCompress get() = chainId

    val getTransaction: TransactionAbi
        @ChildCompress get() = transaction

    val getContextFreeData: List<String>
        @HexCollectionCompress get() = context_free_data
}