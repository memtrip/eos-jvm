package com.memtrip.eos.http.rpc.model.block.response

data class BlockHeaderRootMerkle(
    val _active_nodes: List<String>,
    val _node_count: Int
)