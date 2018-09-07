package com.memtrip.eos.http.aggregation.accountname

import com.memtrip.eos.http.rpc.ChainApi
import com.memtrip.eos.http.rpc.model.account.request.AccountName
import com.memtrip.eos.http.rpc.model.contract.request.GetCurrencyBalance
import io.reactivex.Single

class CheckAccountNameExists(
    private val chainApi: ChainApi
) {

    fun checkAccountNameExists(accountName: String): Single<Boolean> {
        return chainApi.getAccount(AccountName(accountName)).map { it.isSuccessful }
    }
}