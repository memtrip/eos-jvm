package com.memtrip.eos.http.rpc.chain

import com.memtrip.eos.abi.writer.compression.CompressionType
import com.memtrip.eos.http.rpc.Api
import com.memtrip.eos.http.rpc.utils.Config
import com.memtrip.eos.http.rpc.utils.DateAdapter
import com.memtrip.eos.http.rpc.utils.testabi.AbiBinaryGenTransferWriter
import com.memtrip.eos.http.rpc.utils.testabi.TransferArgs
import com.memtrip.eos.http.rpc.utils.testabi.TransferBody

import com.squareup.moshi.Moshi
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(JUnitPlatform::class)
class ChainAbiJsonToBinTest : Spek({

    given("an Api") {

        val okHttpClient by memoized {
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build()
        }

        val chainApi by memoized { Api(Config.CHAIN_API_BASE_URL, okHttpClient).chain }

        on("v1/chain/abi_json_to_bin") {

            val transferBody = TransferBody(
                TransferArgs(
                    "user",
                    "tester",
                    "25.0000 SYS",
                    "here is some coins!")
            )

            val jsonAdapter = Moshi.Builder()
                .add(DateAdapter())
                .build()
                .adapter<TransferBody>(TransferBody::class.java)

            val abi = chainApi.abiJsonToBin(
                RequestBody.create(MediaType.parse("application/json"), jsonAdapter.toJson(transferBody))
            ).blockingGet()

            it("should return the bin") {
                assertTrue(abi.isSuccessful)
                val localAbiBin = AbiBinaryGenTransferWriter(CompressionType.NONE).squishTransferBody(transferBody).toHex()
                assertEquals(abi.body()!!.binargs, localAbiBin)
            }
        }
    }
})