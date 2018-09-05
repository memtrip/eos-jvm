package com.memtrip.eos.http.rpc.model.block.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CycleSummary(
    val read_locks: List<Any>, /* TODO: clarify */
    val write_locks: List<Any>, /* TODO: clarify */
    val transactions: List<CycleSummaryTransaction>
)