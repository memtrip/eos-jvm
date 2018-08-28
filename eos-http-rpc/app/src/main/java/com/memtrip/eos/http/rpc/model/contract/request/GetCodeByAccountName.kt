package com.memtrip.eos.http.rpc.model.contract.request

data class GetCodeByAccountName(
    val account_name: String,
    val code_as_wasm: Boolean
)
