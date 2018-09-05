package com.memtrip.eos.http.aggregation.producer.bpjson.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BpNode(
    val location: BpLocation,
    val node_type: String,
    val api_endpoint: String?,
    val ssl_endpoint: String?,
    val p2p_endpoint: String?
)