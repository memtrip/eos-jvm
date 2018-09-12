package com.memtrip.eos.chain.actions.transaction.account.actions.newaccount

import com.memtrip.eos.abi.writer.Abi
import com.memtrip.eos.abi.writer.CollectionCompress
import com.memtrip.eos.abi.writer.IntCompress
import com.memtrip.eos.abi.writer.StringCollectionCompress

@Abi
data class AccountRequiredAuthAbi(
    val threshold: Int,
    val keys: List<AccountKeyAbi>,
    val accounts: List<String>,
    val waits: List<String>
) {
    val getThreshold: Int
        @IntCompress get() = threshold

    val getKeys: List<AccountKeyAbi>
        @CollectionCompress get() = keys

    val getAccounts: List<String>
        @StringCollectionCompress get() = accounts

    val getWaits: List<String>
        @StringCollectionCompress get() = waits
}
