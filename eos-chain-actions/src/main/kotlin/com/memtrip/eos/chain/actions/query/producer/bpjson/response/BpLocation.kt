package com.memtrip.eos.chain.actions.query.producer.bpjson.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BpLocation(
    val name: String,
    val country: String,
    val latitude: Double,
    val longitude: Double
)