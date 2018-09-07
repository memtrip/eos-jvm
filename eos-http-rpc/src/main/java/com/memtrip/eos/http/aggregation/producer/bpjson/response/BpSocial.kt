package com.memtrip.eos.http.aggregation.producer.bpjson.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BpSocial(
    val steemit: String?,
    val twitter: String?,
    val youtube: String?,
    val facebook: String?,
    val github: String?,
    val reddit: String?,
    val telegram: String?,
    val wechat: String?
)