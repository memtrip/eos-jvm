package com.memtrip.eos.http.rpc.model.account.response

import com.memtrip.eos.abi.writer.Abi
import com.memtrip.eos.abi.writer.CollectionCompress
import com.memtrip.eos.abi.writer.IntCompress
import com.memtrip.eos.abi.writer.StringCollectionCompress
import com.squareup.moshi.JsonClass

@Abi
@JsonClass(generateAdapter = true)
data class AccountRequiredAuth(
    val threshold: Int,
    val keys: List<AccountKey>,
    val accounts: List<String>,
    val waits: List<String>
) {

    val getThreshold: Int
        @IntCompress get() = threshold

    val getKeys: List<AccountKey>
        @CollectionCompress get() = keys

    val getAccounts: List<String>
        @StringCollectionCompress get() = accounts

    val getWaits: List<String>
        @StringCollectionCompress get() = waits
}