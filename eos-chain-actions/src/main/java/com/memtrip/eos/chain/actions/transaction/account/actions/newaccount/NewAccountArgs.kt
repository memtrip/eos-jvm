package com.memtrip.eos.chain.actions.transaction.account.actions.newaccount

import com.memtrip.eos.abi.writer.Abi
import com.memtrip.eos.abi.writer.AccountNameCompress
import com.memtrip.eos.abi.writer.ChildCompress

@Abi
data class NewAccountArgs(
    val creator: String,
    val name: String,
    val owner: AccountRequiredAuthAbi,
    val active: AccountRequiredAuthAbi
) {

    val getCreator: String
        @AccountNameCompress get() = creator

    val getName: String
        @AccountNameCompress get() = name

    val getOwner: AccountRequiredAuthAbi
        @ChildCompress get() = owner

    val getActive: AccountRequiredAuthAbi
        @ChildCompress get() = active
}