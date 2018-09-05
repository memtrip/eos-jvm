package com.memtrip.eos.http.rpc.model.producer.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetProducers(
    val json: Boolean,
    val lower_bound: String,
    val limit: Int
)