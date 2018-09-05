package com.memtrip.eos.http.aggregation.producer.bpjson

import com.memtrip.eos.http.aggregation.AggregateResponse
import com.memtrip.eos.http.aggregation.producer.bpjson.response.BpParent
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
    fun get(url: String): AggregateResponse<BpParent> {
        val patchedUrl = patchUrl(url)
        return try {
            val response = okHttpClient.newCall(
                Request.Builder().url("${patchedUrl}bp.json").build()
            ).execute()

            return if (response.isSuccessful) {
                try {
                    AggregateResponse(
                        true,
                    response.code(),
                    moshi.adapter(BpParent::class.java).fromJson(response.body()!!.string()),
                    null)
                } catch (e: Exception) {
                    AggregateResponse.error<BpParent>()
                }
            } else {
                AggregateResponse.error()
            }
        } catch (e: IOException) {
            AggregateResponse.error()
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