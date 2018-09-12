package com.memtrip.eos.chain.actions.transaction.abi

import com.memtrip.eos.abi.writer.Abi
import com.memtrip.eos.abi.writer.AccountNameCompress
import com.memtrip.eos.abi.writer.CollectionCompress
import com.memtrip.eos.abi.writer.DataCompress
import com.memtrip.eos.abi.writer.NameCompress

@Abi
data class ActionAbi(
    val account: String,
    val name: String,
    val authorization: List<TransactionAuthorizationAbi>,
    val data: String?
) {

    val getAccount: String
        @AccountNameCompress get() = account

    val getName: String
        @NameCompress get() = name

    val getAuthorization: List<TransactionAuthorizationAbi>
        @CollectionCompress get() = authorization

    val getData: String?
        @DataCompress get() = data
}
