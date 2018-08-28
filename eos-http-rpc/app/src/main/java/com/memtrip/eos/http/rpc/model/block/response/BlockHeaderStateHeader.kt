package com.memtrip.eos.http.rpc.model.block.response

import org.threeten.bp.LocalDateTime

data class BlockHeaderStateHeader(
    val timestamp: LocalDateTime,
    val producer: String,
    val confirmed: Int,
    val previous: String,
    val transaction_mroot: String,
    val action_mroot: String,
    val schedule_version: Int,
    val header_extensions: List<Any>,
    val producer_signature: String
)