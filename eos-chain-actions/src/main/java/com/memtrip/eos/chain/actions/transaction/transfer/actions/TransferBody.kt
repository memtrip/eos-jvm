package com.memtrip.eos.chain.actions.transaction.transfer.actions

import com.memtrip.eos.abi.writer.Abi
import com.memtrip.eos.abi.writer.ChildCompress

@Abi
data class TransferBody(
    val code: String,
    val action: String,
    val args: TransferArgs
) {

    val getArgs: TransferArgs
        @ChildCompress get() = args
}