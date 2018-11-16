package com.memtrip.eos.http.rpc.utils.testjson

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TransferBodyJson(
    val code: String,
    val action: String,
    val args: TransferArgsJson
)