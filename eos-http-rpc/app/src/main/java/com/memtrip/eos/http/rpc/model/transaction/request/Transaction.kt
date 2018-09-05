package com.memtrip.eos.http.rpc.model.transaction.request

import com.memtrip.eos.abi.writer.Abi
import com.memtrip.eos.abi.writer.BlockNumCompress
import com.memtrip.eos.abi.writer.BlockPrefixCompress
import com.memtrip.eos.abi.writer.CollectionCompress
import com.memtrip.eos.abi.writer.StringCollectionCompress
import com.memtrip.eos.abi.writer.TimestampCompress
import com.memtrip.eos.abi.writer.VariableUIntCompress
import com.squareup.moshi.JsonClass
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset

@Abi
@JsonClass(generateAdapter = true)
class Transaction(
    val expiration: LocalDateTime,
    val ref_block_num: Int,
    val ref_block_prefix: Long,
    val max_net_usage_words: Long,
    val max_cpu_usage_ms: Long,
    val delay_sec: Long,
    val context_free_actions: List<Action>,
    val actions: List<Action>,
    val transaction_extensions: List<String>,
    val signatures: List<String>,
    val context_free_data: List<String>) {

    val getExpiration: Long
        @TimestampCompress get() = expiration.toInstant(ZoneOffset.UTC).toEpochMilli()

    val getRefBlockNum: Int
        @BlockNumCompress get() = ref_block_num

    val getRefBlockPrefix: Long
        @BlockPrefixCompress get() = ref_block_prefix

    val getMaxNetUsageWords: Long
        @VariableUIntCompress get() = max_net_usage_words

    val getMaxCpuUsageMs: Long
        @VariableUIntCompress get() = max_cpu_usage_ms

    val getDelaySec: Long
        @VariableUIntCompress get() = delay_sec

    val getContextFreeActions: List<Action>
        @CollectionCompress get() = context_free_actions

    val getActions: List<Action>
        @CollectionCompress get() = actions

    val getTransactionExtensions: List<String>
        @StringCollectionCompress get() = transaction_extensions
}