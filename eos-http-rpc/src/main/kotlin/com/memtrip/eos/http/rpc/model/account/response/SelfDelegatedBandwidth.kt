package com.memtrip.eos.http.rpc.model.account.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SelfDelegatedBandwidth(
    val from: String,
    val to: String,
    val net_weight: String,
    val cpu_weight: String
)