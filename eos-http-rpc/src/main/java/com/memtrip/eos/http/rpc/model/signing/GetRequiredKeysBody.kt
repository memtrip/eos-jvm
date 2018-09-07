package com.memtrip.eos.http.rpc.model.signing

import com.memtrip.eos.http.rpc.model.transaction.request.Transaction
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetRequiredKeysBody(
    val transaction: Transaction,
    val available_keys: List<String>
)