package com.memtrip.eos.http.rpc.model.account.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccountName(val account_name: String)