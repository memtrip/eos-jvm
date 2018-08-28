package com.memtrip.eos.http.aggregation.account.actions.buyram

import com.memtrip.eos.abi.writer.Abi
import com.memtrip.eos.abi.writer.AccountNameCompress
import com.memtrip.eos.abi.writer.AssetCompress

@Abi
data class BuyRamArgs(
    val payer: String,
    val receiver: String,
    val quant: String) {

    val getCreator: String
        @AccountNameCompress get() = payer

    val getName: String
        @AccountNameCompress get() = receiver

    val getQuant: String
        @AssetCompress get() = quant
}