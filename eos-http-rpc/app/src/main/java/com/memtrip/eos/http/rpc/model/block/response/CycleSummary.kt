package com.memtrip.eos.http.rpc.model.block.response

data class CycleSummary(
    val read_locks: List<Any>, /* TODO: clarify */
    val write_locks: List<Any>, /* TODO: clarify */
    val transactions: List<CycleSummaryTransaction>
)