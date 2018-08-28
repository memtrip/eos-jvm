package com.memtrip.eos.http.rpc.model.account.response

data class AccountResourceLimit(
    val used: Long,
    val available: Long,
    val max: Long)
