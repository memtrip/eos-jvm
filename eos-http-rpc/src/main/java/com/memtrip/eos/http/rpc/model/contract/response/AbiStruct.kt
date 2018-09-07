package com.memtrip.eos.http.rpc.model.contract.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AbiStruct(
    val name: String,
    val base: String,
    val fields: List<AbiField>
)
