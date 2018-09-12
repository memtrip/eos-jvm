package com.memtrip.eos.chain.actions.transaction.account.actions.newaccount

import com.memtrip.eos.abi.writer.Abi
import com.memtrip.eos.abi.writer.PublicKeyCompress
import com.memtrip.eos.abi.writer.ShortCompress

@Abi
data class AccountKeyAbi(
    val key: String,
    val weight: Short
) {

    val getKey: String
        @PublicKeyCompress get() = key

    val getWeight: Short
        @ShortCompress get() = weight
}
