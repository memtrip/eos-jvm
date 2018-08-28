package com.memtrip.eos.http.rpc.model.contract.request

data class GetTableRows(
    val scope: String,
    val code: String,
    val table: String,
    val json: Boolean,
    val lower_bound: Int = 1,
    val upper_bound: Int = -1,
    val limit: Int = 10
)