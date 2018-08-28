package com.memtrip.eos.http.rpc.model.account.response

import org.threeten.bp.LocalDateTime

data class Account(
    val account_name: String,
    val head_block_num: Int,
    val head_block_time: LocalDateTime,
    val privileged: Boolean,
    val last_code_update: LocalDateTime,
    val created: LocalDateTime,
    val ram_quota: Long,
    val net_weight: Long,
    val cpu_weight: Long,
    val net_limit: AccountResourceLimit,
    val cpu_limit: AccountResourceLimit,
    val ram_usage: Int,
    val permissions: List<AccountPermission>,
    val total_resources: Any?,
    val self_delegated_bandwidth: Any?,
    val refund_request: Any?,
    val voter_info: Any?)