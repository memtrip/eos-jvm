package com.memtrip.eos.http.aggregation

data class AggregateResponse<T> (
    val isSuccessful: Boolean,
    val statusCode: Int,
    val body: T?,
    val errorBody: String?
) {

    companion object {
        fun <T> error(): AggregateResponse<T> {
            return AggregateResponse(
                false,
                400,
                null,
                null)
        }
    }
}