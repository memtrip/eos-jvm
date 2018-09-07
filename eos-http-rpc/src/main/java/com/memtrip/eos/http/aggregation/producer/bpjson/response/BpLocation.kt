package com.memtrip.eos.http.aggregation.producer.bpjson.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BpLocation(
    val name: String,
    val country: String,
    val latitude: Double,
    val longitude: Double
)