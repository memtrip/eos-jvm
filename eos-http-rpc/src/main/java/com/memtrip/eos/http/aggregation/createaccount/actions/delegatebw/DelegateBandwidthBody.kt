package com.memtrip.eos.http.aggregation.createaccount.actions.delegatebw

import com.memtrip.eos.abi.writer.Abi
import com.memtrip.eos.abi.writer.ChildCompress

@Abi
data class DelegateBandwidthBody(
    val code: String,
    val action: String,
    val args: DelegateBandwidthArgs
) {

    val getArgs: DelegateBandwidthArgs
        @ChildCompress get() = args
}