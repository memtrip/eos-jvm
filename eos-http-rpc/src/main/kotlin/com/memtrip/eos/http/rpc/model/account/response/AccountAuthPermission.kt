package com.memtrip.eos.http.rpc.model.account.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccountAuthPermission(
    val actor: String,
    val permission: String
)