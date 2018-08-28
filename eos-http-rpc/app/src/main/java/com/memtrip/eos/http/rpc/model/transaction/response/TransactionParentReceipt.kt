package com.memtrip.eos.http.rpc.model.transaction.response

data class TransactionParentReceipt(
    val status: String,
    val cpu_usage_us: Int,
    val net_usage_words: Int)