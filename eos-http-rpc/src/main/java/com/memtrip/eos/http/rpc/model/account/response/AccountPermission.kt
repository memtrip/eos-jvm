package com.memtrip.eos.http.rpc.model.account.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccountPermission(
    val perm_name: String,
    val parent: String,
    val required_auth: AccountRequiredAuth
)