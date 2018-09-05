package com.memtrip.eos.http.rpc.model.contract.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RawCodeForAccount(
    val account_name: String,
    val wasm: String,
    val abi: String
)