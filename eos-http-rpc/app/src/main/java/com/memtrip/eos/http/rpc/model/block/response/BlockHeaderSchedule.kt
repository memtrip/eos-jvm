package com.memtrip.eos.http.rpc.model.block.response

data class BlockHeaderSchedule(
    val version: Int,
    val producers: List<BlockHeaderProducer>
)