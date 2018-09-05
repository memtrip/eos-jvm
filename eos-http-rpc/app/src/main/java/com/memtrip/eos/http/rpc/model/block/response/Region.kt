package com.memtrip.eos.http.rpc.model.block.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Region(
    val region: Int,
    val cycles_summary: List<CycleSummary>
)
