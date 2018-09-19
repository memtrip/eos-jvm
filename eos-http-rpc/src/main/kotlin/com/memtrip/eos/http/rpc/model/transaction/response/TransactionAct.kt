package com.memtrip.eos.http.rpc.model.transaction.response

import com.memtrip.eos.http.rpc.model.transaction.TransactionAuthorization
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TransactionAct(
    val account: String,
    val name: String,
    val authorization: List<TransactionAuthorization>,
    val data: Any,
    val hex_data: String
)