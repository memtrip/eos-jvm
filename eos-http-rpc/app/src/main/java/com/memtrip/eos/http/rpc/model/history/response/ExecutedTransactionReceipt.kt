package com.memtrip.eos.http.rpc.model.history.response

data class ExecutedTransactionReceipt(
    val status: String,
    val cpu_usage_us: Int,
    val net_usage_words: Int,
    val trx: List<Any>
)