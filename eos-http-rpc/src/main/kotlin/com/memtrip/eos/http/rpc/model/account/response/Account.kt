/**
 * Copyright 2013-present memtrip LTD.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
    val core_liquid_balance: String?,
    val ram_quota: Long,
    val net_weight: Long,
    val cpu_weight: Long,
    val net_limit: AccountResourceLimit,
    val cpu_limit: AccountResourceLimit,
    val ram_usage: Long,
    val permissions: List<AccountPermission>,
    val total_resources: TotalResources?,
    val self_delegated_bandwidth: SelfDelegatedBandwidth?,
    val refund_request: Any?,
    val voter_info: VoterInfo?
)