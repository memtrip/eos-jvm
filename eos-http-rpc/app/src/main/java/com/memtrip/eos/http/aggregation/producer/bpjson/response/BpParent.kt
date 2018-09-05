package com.memtrip.eos.http.aggregation.producer.bpjson.response

import com.memtrip.eos.http.aggregation.producer.BpNode
import com.memtrip.eos.http.aggregation.producer.BpOrg
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BpParent(
    val producer_account_name: String,
    val org: BpOrg,
    val nodes: List<BpNode>
)