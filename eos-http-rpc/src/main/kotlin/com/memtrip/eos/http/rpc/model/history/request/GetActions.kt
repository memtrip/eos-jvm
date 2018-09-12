package com.memtrip.eos.http.rpc.model.history.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetActions(
    val account_name: String,
    val pos: Int? = null,
    val offset: Int? = null
)