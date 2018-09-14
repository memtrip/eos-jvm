package com.memtrip.eos.chain.actions.transaction.account.actions.undelegatebw

import com.memtrip.eos.abi.writer.Abi
import com.memtrip.eos.abi.writer.ChildCompress

@Abi
data class UnDelegateBandwidthBody(
    val code: String,
    val action: String,
    val args: UnDelegateBandwidthArgs
) {

    val getArgs: UnDelegateBandwidthArgs
        @ChildCompress get() = args
}