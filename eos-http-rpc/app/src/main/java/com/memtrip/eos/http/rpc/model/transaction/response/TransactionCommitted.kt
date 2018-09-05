package com.memtrip.eos.http.rpc.model.transaction.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TransactionCommitted(
    val transaction_id: String,
    val processed: TransactionProcessed
)