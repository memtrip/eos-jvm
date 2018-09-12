package com.memtrip.eos.http.rpc.utils.testabi

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