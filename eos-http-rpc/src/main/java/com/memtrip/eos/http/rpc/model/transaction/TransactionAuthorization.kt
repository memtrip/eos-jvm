package com.memtrip.eos.http.rpc.model.transaction

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TransactionAuthorization(
    val actor: String,
    val permission: String
)