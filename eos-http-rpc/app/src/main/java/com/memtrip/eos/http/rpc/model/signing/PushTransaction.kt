package com.memtrip.eos.http.rpc.model.signing

data class PushTransaction(
    val signatures: List<String>,
    val compression: String,
    val packed_context_free_data: String,
    val packed_trx: String)