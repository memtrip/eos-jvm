package com.memtrip.eos.http.rpc.model.contract.response

data class BinaryHex(
    val binargs: String,
    val required_scope: List<String>?,
    val required_auth: List<String>?)