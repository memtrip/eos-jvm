package com.memtrip.eos.http.rpc.model.transaction

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Action(
    val account: String,
    val name: String,
    val authorization: List<TransactionAuthorization>,
    val data: String?
)