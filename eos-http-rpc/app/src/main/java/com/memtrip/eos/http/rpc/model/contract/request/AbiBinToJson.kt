package com.memtrip.eos.http.rpc.model.contract.request

data class AbiBinToJson(
    val code: String,
    val action: String,
    val binargs: String
)