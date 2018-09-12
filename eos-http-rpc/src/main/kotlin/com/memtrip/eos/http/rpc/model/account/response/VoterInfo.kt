package com.memtrip.eos.http.rpc.model.account.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VoterInfo(
    val owner: String,
    val proxy: String,
    val producers: List<String>,
    val staked: Double,
    val last_vote_weight: Double,
    val proxied_vote_weight: Double,
    val is_proxy: Int
)
