package com.memtrip.eos.http.rpc.model.history.response

import com.memtrip.eos.http.rpc.model.transaction.response.TransactionActionTrace
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class HistoricTransaction(
    val id: String,
    val trx: ExecutedTransactionParent,
    val block_time: Date,
    val block_num: Int,
    val last_irreversible_block: Int,
    val traces: List<TransactionActionTrace>
)