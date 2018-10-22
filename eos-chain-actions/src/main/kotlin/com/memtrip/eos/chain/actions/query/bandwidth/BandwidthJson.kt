package com.memtrip.eos.chain.actions.query.bandwidth

data class BandwidthJson(
    val from: String,
    val to: String,
    val net_weight: String,
    val cpu_weight: String
)