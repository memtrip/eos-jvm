package com.memtrip.eos.http.aggregation

data class TransactionResponse<T> (
    val isSuccessful: Boolean,
    val statusCode: Int,
    val body: T?,
    val errorBody: String?
)