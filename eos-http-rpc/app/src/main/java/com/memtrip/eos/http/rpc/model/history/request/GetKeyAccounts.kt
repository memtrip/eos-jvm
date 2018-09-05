package com.memtrip.eos.http.rpc.model.history.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetKeyAccounts(
    val public_key: String
)