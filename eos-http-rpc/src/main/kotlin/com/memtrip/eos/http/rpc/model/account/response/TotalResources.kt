package com.memtrip.eos.http.rpc.model.account.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TotalResources(
    val owner: String,
    val net_weight: String,
    val cpu_weight: String,
    val ram_bytes: Long
)