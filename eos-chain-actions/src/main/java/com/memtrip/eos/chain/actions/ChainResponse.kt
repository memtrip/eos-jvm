package com.memtrip.eos.chain.actions

data class ChainResponse<T> (
    val isSuccessful: Boolean,
    val statusCode: Int,
    val body: T?,
    val errorBody: String?
) {

    companion object {
        fun <T> error(): ChainResponse<T> {
            return ChainResponse(
                false,
                400,
                null,
                null)
        }
    }
}