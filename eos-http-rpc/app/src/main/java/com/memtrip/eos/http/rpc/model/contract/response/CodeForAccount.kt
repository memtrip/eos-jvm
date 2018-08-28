package com.memtrip.eos.http.rpc.model.contract.response

data class CodeForAccount(
    val account_name: String,
    val code_hash: String,
    val wast: String,
    val wasm: String
)
