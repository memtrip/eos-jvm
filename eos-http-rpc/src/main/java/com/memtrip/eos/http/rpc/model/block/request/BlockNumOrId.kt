package com.memtrip.eos.http.rpc.model.block.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BlockNumOrId(val block_num_or_id: String)