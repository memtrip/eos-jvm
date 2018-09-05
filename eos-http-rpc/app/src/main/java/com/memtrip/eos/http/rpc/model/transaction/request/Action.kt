package com.memtrip.eos.http.rpc.model.transaction.request

import com.memtrip.eos.abi.writer.Abi
import com.memtrip.eos.abi.writer.AccountNameCompress
import com.memtrip.eos.abi.writer.CollectionCompress
import com.memtrip.eos.abi.writer.DataCompress
import com.memtrip.eos.abi.writer.NameCompress
import com.memtrip.eos.http.rpc.model.transaction.TransactionAuthorization
import com.squareup.moshi.JsonClass

@Abi
@JsonClass(generateAdapter = true)
data class Action(
    val account: String,
    val name: String,
    val authorization: List<TransactionAuthorization>,
    val data: String?
) {

    val getAccount: String
        @AccountNameCompress get() = account

    val getName: String
        @NameCompress get() = name

    val getAuthorization: List<TransactionAuthorization>
        @CollectionCompress get() = authorization

    val getData: String?
        @DataCompress get() = data
}
