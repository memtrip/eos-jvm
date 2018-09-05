package com.memtrip.eos.http.rpc.model.account.response

import com.memtrip.eos.abi.writer.Abi
import com.memtrip.eos.abi.writer.PublicKeyCompress
import com.memtrip.eos.abi.writer.ShortCompress
import com.squareup.moshi.JsonClass

@Abi
@JsonClass(generateAdapter = true)
data class AccountKey(
    val key: String,
    val weight: Short) {

    val getKey: String
        @PublicKeyCompress get() = key

    val getWeight: Short
        @ShortCompress get() = weight
}