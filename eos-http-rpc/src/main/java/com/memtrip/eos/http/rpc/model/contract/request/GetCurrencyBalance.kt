package com.memtrip.eos.http.rpc.model.contract.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetCurrencyBalance(
    val code: String,
    val account: String,
    val symbol: String? = null
)