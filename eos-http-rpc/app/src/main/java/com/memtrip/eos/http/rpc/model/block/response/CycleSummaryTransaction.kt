package com.memtrip.eos.http.rpc.model.block.response

data class CycleSummaryTransaction(
    val id: String,
    val status: String,
    val kcpu_usage: Int,
    val net_usage_words: Int
)