package com.memtrip.eos.http.rpc.utils.testabi

import com.memtrip.eos.abi.writer.Abi
import com.memtrip.eos.abi.writer.AccountNameCompress
import com.memtrip.eos.abi.writer.AssetCompress
import com.memtrip.eos.abi.writer.StringCompress

@Abi
data class TransferArgs(
    val from: String,
    val to: String,
    val quantity: String,
    val memo: String
) {

    val getFrom: String
        @AccountNameCompress get() = from

    val getTo: String
        @AccountNameCompress get() = to

    val getQuantity: String
        @AssetCompress get() = quantity

    val getMemo: String
        @StringCompress get() = memo
}