package com.memtrip.eos.http.rpc.model.block.response

data class Region(
    val region: Int,
    val cycles_summary: List<CycleSummary>
)
