/**
 * Copyright 2013-present memtrip LTD.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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