package com.memtrip.eos.http.rpc.model.block.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CycleSummaryTransaction(
    val id: String,
    val status: String,
    val kcpu_usage: Int,
    val net_usage_words: Int
)