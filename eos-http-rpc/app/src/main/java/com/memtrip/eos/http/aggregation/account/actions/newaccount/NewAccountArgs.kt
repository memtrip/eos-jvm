package com.memtrip.eos.http.aggregation.account.actions.newaccount

import com.memtrip.eos.abi.writer.Abi
import com.memtrip.eos.abi.writer.AccountNameCompress
import com.memtrip.eos.abi.writer.ChildCompress
import com.memtrip.eos.http.rpc.model.account.response.AccountRequiredAuth

@Abi
data class NewAccountArgs(
    val creator: String,
    val name: String,
    val owner: AccountRequiredAuth,
    val active: AccountRequiredAuth) {

    val getCreator: String
        @AccountNameCompress get() = creator

    val getName: String
        @AccountNameCompress get() = name

    val getOwner: AccountRequiredAuth
        @ChildCompress get() = owner

    val getActive: AccountRequiredAuth
        @ChildCompress get() = active
}