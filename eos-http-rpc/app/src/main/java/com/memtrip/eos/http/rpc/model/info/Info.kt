package com.memtrip.eos.http.rpc.model.info

data class Info(
    val server_version: String,
    val chain_id: String,
    val head_block_num: Int,
    val last_irreversible_block_num: Int,
    val head_block_id: String,
    val head_block_time: String,
    val head_block_producer: String)