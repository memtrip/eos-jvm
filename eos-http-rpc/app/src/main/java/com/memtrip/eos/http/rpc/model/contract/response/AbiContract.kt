package com.memtrip.eos.http.rpc.model.contract.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AbiContract(
    val version: String,
    val types: List<AbiType>,
    val structs: List<AbiStruct>,
    val actions: List<AbiAction>,
    val tables: List<AbiTable>,
    val ricardian_clauses: List<Any>,
    val error_messages: List<Any>,
    val abi_extensions: List<Any>
)
