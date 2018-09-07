package com.memtrip.eos.http.aggregation.producer.bpjson.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BpOrg(
    val candidate_name: String,
    val website: String,
    val code_of_conduct: String,
    val ownership_disclosure: String,
    val email: String,
    val branding: BpBranding,
    val location: BpLocation,
    val social: BpSocial
)