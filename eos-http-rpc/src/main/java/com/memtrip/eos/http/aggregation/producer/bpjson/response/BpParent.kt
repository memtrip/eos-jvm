package com.memtrip.eos.http.aggregation.producer.bpjson.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BpParent(
    val producer_account_name: String,
    val org: BpOrg,
    val nodes: List<BpNode>
)