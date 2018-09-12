package com.memtrip.eos.http.rpc.model.account.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccountRequiredAuth(
    val threshold: Int,
    val keys: List<AccountKey>,
    val accounts: List<String>,
    val waits: List<String>
)