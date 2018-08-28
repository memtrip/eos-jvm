package com.memtrip.eos.http.rpc.model.contract.response

data class AbiStruct(
    val name: String,
    val base: String,
    val fields: List<AbiField>
)
