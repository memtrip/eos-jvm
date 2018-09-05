package com.memtrip.eos.http.rpc.model.contract.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AbiBinToJson(
    val code: String,
    val action: String,
    val binargs: String
)