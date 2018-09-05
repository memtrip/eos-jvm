package com.memtrip.eos.http.rpc.model.history.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ExecutedTransactionParent(
    val receipt: ExecutedTransactionReceipt,
    val trx: ExecutedTransaction
)