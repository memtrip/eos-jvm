package com.memtrip.eos.http.aggregation.account.actions.buyram

import com.memtrip.eos.abi.writer.Abi
import com.memtrip.eos.abi.writer.ChildCompress

@Abi
data class BuyRamBody(
    val args: BuyRamArgs
) {

    val getArgs: BuyRamArgs
        @ChildCompress get() = args
}