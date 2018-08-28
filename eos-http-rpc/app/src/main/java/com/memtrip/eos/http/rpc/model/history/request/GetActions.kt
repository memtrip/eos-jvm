package com.memtrip.eos.http.rpc.model.history.request

data class GetActions(
    val account_name: String,
    val pos: Int? = null,
    val offset: Int? = null
)