package com.memtrip.eos.http.rpc.model.transaction.request

import com.memtrip.eos.abi.writer.Abi
import com.memtrip.eos.abi.writer.ChainIdCompress
import com.memtrip.eos.abi.writer.ChildCompress
import com.memtrip.eos.abi.writer.HexCollectionCompress
import com.squareup.moshi.JsonClass

@Abi
@JsonClass(generateAdapter = true)
data class SignedTransaction(
    val chainId: String,
    val transaction: Transaction,
    val context_free_data: List<String>
) {
    val getChainId: String
        @ChainIdCompress get() = chainId

    val getTransaction: Transaction
        @ChildCompress get() = transaction

    val getContextFreeData: List<String>
        @HexCollectionCompress get() = context_free_data
}