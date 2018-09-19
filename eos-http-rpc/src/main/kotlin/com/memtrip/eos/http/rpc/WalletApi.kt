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

import com.memtrip.eos.http.rpc.model.transaction.Transaction
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface WalletApi {

    @POST("v1/wallet/create")
    fun create(@Body walletName: String): Single<Response<String>>

    @POST("v1/wallet/open")
    fun open(@Body walletName: String): Single<Response<Void>>

    @POST("v1/wallet/lock")
    fun lock(@Body walletName: String): Single<Response<Void>>

    @POST("v1/wallet/lock_all")
    fun lockAll(): Single<Response<Void>>

    @POST("v1/wallet/unlock")
    fun unlock(@Body walletNamePassword: List<String>): Single<Response<Void>>

    @POST("v1/wallet/import_key")
    fun importKey(@Body walletPrivateKey: List<String>): Single<Response<Void>>

    @POST("v1/wallet/list_wallets")
    fun listWallets(): Single<Response<List<String>>>

    @POST("v1/wallet/list_keys")
    fun listKeys(@Body walletNamePassword: List<String>): Single<Response<List<List<String>>>>

    @POST("v1/wallet/get_public_keys")
    fun getPublicKeys(): Single<Response<List<String>>>

    @POST("v1/wallet/set_timeout")
    fun setTimeout(@Body timeout: Int): Single<Response<Void>>

    @POST("v1/wallet/sign_transaction")
    fun signTransaction(@Body param: RequestBody): Single<Response<Transaction>>

    @POST("v1/wallet/sign_digest")
    fun signDigest(@Body digestKey: List<String>): Single<Response<String>>

    @POST("v1/wallet/create_key")
    fun createKey(@Body accountAndType: List<String>): Single<Response<String>>
}