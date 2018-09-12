package com.memtrip.eos.http.rpc.model.block.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BlockHeaderSchedule(
    val version: Int,
    val producers: List<BlockHeaderProducer>
)