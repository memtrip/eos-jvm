package com.memtrip.eos.http.rpc.model.contract.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AbiType(
    val new_type_name: String,
    val type: String
)