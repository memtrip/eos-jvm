package com.memtrip.eos.chain.actions.transaction.account.actions.refund

import com.memtrip.eos.abi.writer.Abi
import com.memtrip.eos.abi.writer.AccountNameCompress

@Abi
data class RefundArgs(
    val owner: String
) {

    val getOwner: String
        @AccountNameCompress get() = owner
}