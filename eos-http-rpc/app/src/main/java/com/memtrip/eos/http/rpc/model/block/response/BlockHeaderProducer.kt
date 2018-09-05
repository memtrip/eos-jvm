package com.memtrip.eos.http.rpc.model.block.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BlockHeaderProducer(
    val producer_name: String,
    val block_signing_key: String
)