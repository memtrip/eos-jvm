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
import java.util.Date

@JsonClass(generateAdapter = true)
data class Block(
    val id: String,
    val block_num: Int,
    val confirmed: Int,
    val ref_block_prefix: Long,
    val previous: String,
    val timestamp: Date,
    val transaction_mroot: String,
    val action_mroot: String,
    val block_mroot: String?,
    val producer: String,
    val schedule_version: Int,
    val new_producers: Any?,
    val producer_signature: String,
    val regions: List<Region>?,
    val transactions: List<TransactionInBlock>?
)

@JsonClass(generateAdapter = true)
data class TransactionInBlock(
    val status: String,
    val cpu_usage_us: Int,
    val net_usage_words: Int,
    val trx: Any?
)

class Trx(map: Map<String, Any?>) {
    val id: String by map
    val transaction: Map<String, Any> by map
}

class BlockTransaction(map: Map<String, Any?>) {
    val expiration: Date by map
    val ref_block_num: Int by map
    val ref_block_prefix: Long by map
    val max_net_usage_words: Long by map
    val max_cpu_usage_ms: Long by map
    val delay_sec: Long by map
    val context_free_actions: List<Map<String, Any>> by map
    val actions: List<Map<String, Any>> by map
}

class BlockTransactionAction(map: Map<String, Any?>) {
    val account: String by map
    val name: String by map
    val data: Map<String, Any> by map
}

class TransferData(map: Map<String, Any?>) {
    val from: String by map
    val to: String by map
    val quantity: String by map
    val memo: String by map
}