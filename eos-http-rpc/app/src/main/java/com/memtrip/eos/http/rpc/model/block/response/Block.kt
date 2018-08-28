package com.memtrip.eos.http.rpc.model.block.response

import org.threeten.bp.LocalDateTime

data class Block(
    val id: String,
    val block_num: Int,
    val ref_block_prefix: Long,
    val previous: String,
    val timestamp: LocalDateTime,
    val transaction_mroot: String,
    val action_mroot: String,
    val block_mroot: String?,
    val producer: String,
    val schedule_version: Int,
    val new_producers: Any?,
    val producer_signature: String,
    val regions: List<Region>?,
    val input_transactions: List<Any>?)