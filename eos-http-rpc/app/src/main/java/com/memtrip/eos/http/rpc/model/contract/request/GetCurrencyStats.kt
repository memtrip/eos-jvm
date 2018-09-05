package com.memtrip.eos.http.rpc.model.contract.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetCurrencyStats(
    val code: String,
    val symbol: String
)