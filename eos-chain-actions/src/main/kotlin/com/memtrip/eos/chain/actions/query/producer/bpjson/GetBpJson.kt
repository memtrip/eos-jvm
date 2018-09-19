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
package com.memtrip.eos.chain.actions.query.producer.bpjson

import com.memtrip.eos.chain.actions.ChainResponse
import com.memtrip.eos.chain.actions.query.producer.bpjson.response.BpParent
import com.squareup.moshi.Moshi

import okhttp3.OkHttpClient
import okhttp3.Request

import java.io.IOException
import java.util.concurrent.TimeUnit

class GetBpJson(
    private val okHttpClient: OkHttpClient =
        OkHttpClient
            .Builder()
            .connectTimeout(2000, TimeUnit.MILLISECONDS)
            .readTimeout(1500, TimeUnit.MILLISECONDS)
            .build(),
    private val moshi: Moshi = Moshi.Builder().build()
) {
    fun get(url: String): ChainResponse<BpParent> {
        val patchedUrl = patchUrl(url)
        return try {
            val response = okHttpClient.newCall(
                Request.Builder().url("${patchedUrl}bp.json").build()
            ).execute()

            return if (response.isSuccessful) {
                try {
                    ChainResponse(
                        true,
                        response.code(),
                        moshi.adapter(BpParent::class.java).fromJson(response.body()!!.string()),
                        null)
                } catch (e: Exception) {
                    ChainResponse.error<BpParent>()
                }
            } else {
                ChainResponse.error()
            }
        } catch (e: IOException) {
            ChainResponse.error()
        }
    }

    private fun patchUrl(url: String): String {
        if (!url.endsWith("/")) {
            return "$url/"
        } else {
            return url
        }
    }
}