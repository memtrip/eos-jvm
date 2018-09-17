package com.memtrip.eos.http.rpc.model.contract.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetTableRows(
    val scope: String,
    val code: String,
    val table: String,
    val json: Boolean,
    val limit: Int,
    val lower_bound: String,
    val upper_bound: String,
    val key_type: String,
    val index_position: String,
    val encode_type: String
)