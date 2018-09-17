package com.memtrip.eos.http.rpc.model.contract.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContractTableRows(
    val rows: List<Map<String, Any>>,
    val more: Boolean
)