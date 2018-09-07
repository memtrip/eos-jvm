package com.memtrip.eos.http.rpc.model.contract.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AbiTable(
    val name: String,
    val index_type: String,
    val key_names: List<String>,
    val key_types: List<String>,
    val type: String
)