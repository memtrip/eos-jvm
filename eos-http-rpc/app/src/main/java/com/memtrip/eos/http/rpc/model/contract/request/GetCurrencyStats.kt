package com.memtrip.eos.http.rpc.model.contract.request

data class GetCurrencyStats(
    val code: String,
    val symbol: String
)