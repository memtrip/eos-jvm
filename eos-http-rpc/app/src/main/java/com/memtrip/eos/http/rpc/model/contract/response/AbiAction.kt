package com.memtrip.eos.http.rpc.model.contract.response

data class AbiAction(
    val name: String,
    val type: String,
    val ricardian_contract: String
)