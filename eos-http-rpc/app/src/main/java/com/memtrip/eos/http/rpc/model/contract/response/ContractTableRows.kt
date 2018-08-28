package com.memtrip.eos.http.rpc.model.contract.response

data class ContractTableRows(
    val rows: List<Any>,
    val more: Boolean
)