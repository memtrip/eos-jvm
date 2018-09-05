package com.memtrip.eos.http.rpc.model.producer.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Producer(
    val owner: String,
    val total_votes: String,
    val producer_key: String,
    val is_active: Int,
    val url: String,
    val unpaid_blocks: Int,
    val last_claim_time: String,
    val location: Int
)