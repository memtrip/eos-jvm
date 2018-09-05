package com.memtrip.eos.http.aggregation.producer.bpjson

import com.memtrip.eos.http.aggregation.AggregateResponse
import com.memtrip.eos.http.aggregation.producer.bpjson.response.BpParent
import com.squareup.moshi.Moshi

import okhttp3.OkHttpClient
import okhttp3.Request

import java.io.IOException

class GetBpJson(
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder().build(),
    private val moshi: Moshi = Moshi.Builder().build()
) {
    fun get(url: String): AggregateResponse<BpParent> {
        return try {
            val response = okHttpClient.newCall(
                Request.Builder().url("${url}bp.json").build()
            ).execute()

            return if (response.isSuccessful) {
                try {
                    AggregateResponse(
                        true,
                    response.code(),
                    moshi.adapter(BpParent::class.java).fromJson(response.body()!!.string()),
                    null)
                } catch (e: IOException) {
                    AggregateResponse.error<BpParent>()
                }
            } else {
                AggregateResponse.error()
            }
        } catch (e: IOException) {
            AggregateResponse.error()
        }
    }
}