package com.memtrip.eos.chain.actions.transaction.account.actions.refund

import com.memtrip.eos.abi.writer.Abi
import com.memtrip.eos.abi.writer.ChildCompress

@Abi
data class RefundBody(
    val args: RefundArgs
) {

    val getArgs: RefundArgs
        @ChildCompress get() = args
}