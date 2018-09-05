package com.memtrip.eos.http.rpc.model.block.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BlockHeaderState(
    val id: String,
    val block_num: Int,
    val header: BlockHeaderStateHeader,
    val dpos_proposed_irreversible_blocknum: Int,
    val dpos_irreversible_blocknum: Int,
    val bft_irreversible_blocknum: Int,
    val pending_schedule_lib_num: Int,
    val pending_schedule_hash: String,
    val pending_schedule: BlockHeaderSchedule,
    val active_schedule: BlockHeaderSchedule,
    val blockroot_merkle: BlockHeaderRootMerkle,
    val producer_to_last_produced: List<List<Any>>,
    val producer_to_last_implied_irb: List<List<Any>>,
    val block_signing_key: String,
    val confirm_count: List<Any>,
    val confirmations: List<Any>
)
