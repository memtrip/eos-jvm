package com.memtrip.eos.http.rpc.model.account.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccountResourceLimit(
    val used: Long,
    val available: Long,
    val max: Long
)
