package com.memtrip.eos.http.rpc.model.contract.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AbiAction(
    val name: String,
    val type: String,
    val ricardian_contract: String
)