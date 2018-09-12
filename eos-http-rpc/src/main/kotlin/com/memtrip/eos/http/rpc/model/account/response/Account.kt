package com.memtrip.eos.http.rpc.model.account.response

import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class Account(
    val account_name: String,
    val head_block_num: Int,
    val head_block_time: Date,
    val privileged: Boolean,
    val last_code_update: Date,
    val created: Date,
    val ram_quota: Long,
    val net_weight: Long,
    val cpu_weight: Long,
    val net_limit: AccountResourceLimit,
    val cpu_limit: AccountResourceLimit,
    val ram_usage: Long,
    val permissions: List<AccountPermission>,
    val total_resources: Any?,
    val self_delegated_bandwidth: Any?,
    val refund_request: Any?,
    val voter_info: VoterInfo?
)