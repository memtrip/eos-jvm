package com.memtrip.eos.http.rpc.model.contract.response

data class RawCodeForAccount(
    val account_name: String,
    val wasm: String,
    val abi: String
)