package com.memtrip.eos.http.rpc.model.block.response

data class BlockHeaderProducer(
    val producer_name: String,
    val block_signing_key: String
)