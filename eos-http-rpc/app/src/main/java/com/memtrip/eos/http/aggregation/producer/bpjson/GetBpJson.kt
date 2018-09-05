package com.memtrip.eos.http.aggregation.producer.bpjson

import com.memtrip.eos.http.aggregation.producer.BpParent
import com.squareup.moshi.Moshi
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Response

class FetchBpJson(
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder().build(),
    private val moshi: Moshi = Moshi.Builder().build()
) {

    fun get(url: String): Single<Response<BpParent>> {
        return Single.fromCallable { getCallable(url) }
    }

    private fun getCallable(url: String): Response<BpParent> {
        val request = Request.Builder()
            .url(url)
            .build()

        val response = okHttpClient.newCall(request).execute()

        if (response.isSuccessful) {
            return Response.success(moshi.adapter(BpParent::class.java).fromJson(response.body()!!.string()))
        } else {
            return Response.error(response.code(), response.body()!!)
        }
    }
}