package com.memtrip.eos.http.rpc.utils.testjson

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TransferArgsJson(
    val from: String,
    val to: String,
    val quantity: String,
    val memo: String
)