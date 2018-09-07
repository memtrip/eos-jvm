package com.memtrip.eos.http.rpc.model.contract.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetCodeByAccountName(
    val account_name: String,
    val code_as_wasm: Boolean
)
