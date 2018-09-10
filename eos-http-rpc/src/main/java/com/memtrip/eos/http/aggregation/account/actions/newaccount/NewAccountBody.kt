package com.memtrip.eos.http.aggregation.account.actions.newaccount

import com.memtrip.eos.abi.writer.Abi
import com.memtrip.eos.abi.writer.ChildCompress

@Abi
data class NewAccountBody(
    val args: NewAccountArgs
) {

    val getArgs: NewAccountArgs
        @ChildCompress get() = args
}