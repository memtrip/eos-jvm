package com.memtrip.eos.http.rpc

import com.memtrip.eos.http.rpc.model.history.request.GetActions
import com.memtrip.eos.http.rpc.model.history.request.GetControlledAccounts
import com.memtrip.eos.http.rpc.model.history.request.GetKeyAccounts
import com.memtrip.eos.http.rpc.model.history.request.GetTransaction
import com.memtrip.eos.http.rpc.model.history.response.Accounts
import com.memtrip.eos.http.rpc.model.history.response.HistoricAccountActionParent
import com.memtrip.eos.http.rpc.model.history.response.HistoricTransaction
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface HistoryApi {

    @POST("v1/history/get_actions")
    fun getActions(@Body body: GetActions): Single<Response<HistoricAccountActionParent>>

    @POST("v1/history/get_transaction")
    fun getTransaction(@Body body: GetTransaction): Single<Response<HistoricTransaction>>

    @POST("v1/history/get_key_accounts")
    fun getKeyAccounts(@Body body: GetKeyAccounts): Single<Response<Accounts>>

    @POST("v1/history/get_controlled_accounts")
    fun getControlledAccounts(@Body body: GetControlledAccounts): Single<Response<Accounts>>
}