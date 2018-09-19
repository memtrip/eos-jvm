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
package com.memtrip.eos.http.rpc.model.block.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BlockHeaderState(
    val id: String,
    val block_num: Int,
    val header: BlockHeaderStateHeader,
    val dpos_proposed_irreversible_blocknum: Int,
    val dpos_irreversible_blocknum: Int,
    val bft_irreversible_blocknum: Int,
    val pending_schedule_lib_num: Int,
    val pending_schedule_hash: String,
    val pending_schedule: BlockHeaderSchedule,
    val active_schedule: BlockHeaderSchedule,
    val blockroot_merkle: BlockHeaderRootMerkle,
    val producer_to_last_produced: List<List<Any>>,
    val producer_to_last_implied_irb: List<List<Any>>,
    val block_signing_key: String,
    val confirm_count: List<Any>,
    val confirmations: List<Any>
)
