package com.memtrip.eos.http.rpc.model.producer.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProducerList(
    val rows: List<Producer>,
    val total_producer_vote_weight: String,
    val more: String
)