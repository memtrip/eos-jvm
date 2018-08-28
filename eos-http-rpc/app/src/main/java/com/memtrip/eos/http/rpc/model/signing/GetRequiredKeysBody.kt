package com.memtrip.eos.http.rpc.model.signing

import com.memtrip.eos.http.rpc.model.transaction.request.Transaction

data class GetRequiredKeysBody(
    val transaction: Transaction,
    val available_keys: List<String>)