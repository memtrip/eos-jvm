package com.memtrip.eos.http.rpc.model.account.response

import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class RefundRequest(
    val owner: String,
    val request_time: Date,
    val net_amount: String,
    val cpu_amount: String
)