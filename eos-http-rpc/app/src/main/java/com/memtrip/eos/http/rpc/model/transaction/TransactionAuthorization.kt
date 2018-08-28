package com.memtrip.eos.http.rpc.model.transaction

import com.memtrip.eos.abi.writer.Abi
import com.memtrip.eos.abi.writer.AccountNameCompress

@Abi
data class TransactionAuthorization(
    val actor: String,
    val permission: String
) {

    val getActor: String
        @AccountNameCompress get() = actor

    val getPermission: String
        @AccountNameCompress get() = permission
}