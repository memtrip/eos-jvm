package com.memtrip.eos.http.rpc.model.transaction.response

data class TransactionReceipt(
    val receiver: String,
    val act_digest: String,
    val global_sequence: Int,
    val recv_sequence: Int,
    val auth_sequence: List<Any>,
    val code_sequence: Int,
    val abi_sequence: Int)