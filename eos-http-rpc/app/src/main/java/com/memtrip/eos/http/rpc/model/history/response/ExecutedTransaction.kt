package com.memtrip.eos.http.rpc.model.history.response

import com.memtrip.eos.http.rpc.model.transaction.request.Action
import org.threeten.bp.LocalDateTime

class ExecutedTransaction(
    val expiration: LocalDateTime,
    val ref_block_num: Int,
    val ref_block_prefix: Long,
    val max_net_usage_words: Long,
    val max_cpu_usage_ms: Long,
    val delay_sec: Long,
    val context_free_actions: List<Action>,
    val actions: List<Any>,
    val transaction_extensions: List<String>,
    val signatures: List<String>,
    val context_free_data: List<String>
)