package com.memtrip.eos.http.rpc.model.block.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BlockHeaderRootMerkle(
    val _active_nodes: List<String>,
    val _node_count: Int
)