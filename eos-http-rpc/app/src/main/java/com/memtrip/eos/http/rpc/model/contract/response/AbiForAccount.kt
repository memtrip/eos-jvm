package com.memtrip.eos.http.rpc.model.contract.response

data class AbiForAccount(
    val account_name: String,
    val abi: AbiContract
)