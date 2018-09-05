package com.memtrip.eos.http.rpc.model.contract.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CodeForAccount(
    val account_name: String,
    val code_hash: String,
    val wast: String,
    val wasm: String
)
