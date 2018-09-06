package com.memtrip.eos.http.rpc.model.history.response

import com.memtrip.eos.http.rpc.model.transaction.response.TransactionActionTrace
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class HistoricAccountAction(
    val global_action_seq: Int,
    val account_action_seq: Int,
    val block_num: Int,
    val block_time: Date,
    val action_trace: TransactionActionTrace
)