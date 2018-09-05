package com.memtrip.eos.http.rpc.model.signing

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PushTransaction(
    val signatures: List<String>,
    val compression: String,
    val packed_context_free_data: String,
    val packed_trx: String
)