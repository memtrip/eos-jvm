package com.memtrip.eos.http.rpc.model.history.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ExecutedTransactionReceipt(
    val status: String,
    val cpu_usage_us: Int,
    val net_usage_words: Int,
    val trx: List<Any>
)