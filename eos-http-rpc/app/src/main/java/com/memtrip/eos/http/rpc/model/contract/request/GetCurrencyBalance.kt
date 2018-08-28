package com.memtrip.eos.http.rpc.model.contract.request

data class GetCurrencyBalance(
    val code: String,
    val account: String,
    val symbol: String
)