package com.memtrip.eos.http.rpc.model.transaction.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TransactionActionTrace(
    val receipt: TransactionReceipt,
    val act: TransactionAct,
    val elapsed: Int,
    val cpu_usage: Int,
    val console: String,
    val total_cpu_usage: Int,
    val trx_id: String,
    val inline_traces: List<TransactionActionTrace>
)
