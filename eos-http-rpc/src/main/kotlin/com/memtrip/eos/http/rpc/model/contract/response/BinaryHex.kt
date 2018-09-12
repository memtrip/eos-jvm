package com.memtrip.eos.http.rpc.model.contract.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BinaryHex(
    val binargs: String,
    val required_scope: List<String>?,
    val required_auth: List<String>?
)