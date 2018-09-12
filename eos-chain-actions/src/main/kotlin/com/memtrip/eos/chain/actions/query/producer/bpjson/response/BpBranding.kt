package com.memtrip.eos.chain.actions.query.producer.bpjson.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BpBranding(
    val logo_256: String?,
    val logo_1024: String?,
    val logo_svg: String?
)
