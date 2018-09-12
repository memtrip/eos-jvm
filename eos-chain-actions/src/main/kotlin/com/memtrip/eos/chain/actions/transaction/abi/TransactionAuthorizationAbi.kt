package com.memtrip.eos.chain.actions.transaction.abi

import com.memtrip.eos.abi.writer.Abi
import com.memtrip.eos.abi.writer.AccountNameCompress

@Abi
data class TransactionAuthorizationAbi(
    val actor: String,
    val permission: String
) {

    val getActor: String
        @AccountNameCompress get() = actor

    val getPermission: String
        @AccountNameCompress get() = permission
}