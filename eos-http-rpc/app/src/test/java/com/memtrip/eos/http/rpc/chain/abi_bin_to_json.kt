package com.memtrip.eos.http.rpc.chain

import com.memtrip.eos.abi.writer.compression.CompressionType
import com.memtrip.eos.http.aggregation.transfer.actions.TransferArgs
import com.memtrip.eos.http.aggregation.transfer.actions.TransferBody
import com.memtrip.eos.http.rpc.Api
import com.memtrip.eos.http.rpc.Config
import com.memtrip.eos.http.rpc.model.contract.request.AbiBinToJson
import com.memtrip.eos.http.rpc.utils.LocalDateTimeAdapter
import com.memtrip.eosio.abi.binary.gen.AbiBinaryGen
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(JUnitPlatform::class)
class ChainAbiBinToJsonTest : Spek({

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

        on("v1/chain/abi_bin_to_json") {

            /**
             * Create abi
             */
            val transferBody = TransferBody(
                "eosio.token",
                "transfer",
                TransferArgs(
                    "user",
                    "tester",
                    "25.0000 SYS",
                    "here is some coins!")
            )

            val abi = AbiBinaryGen(CompressionType.NONE).squishTransferBody(transferBody).toHex()

            /**
             * v1/chain/abi_bin_to_json
             */
            val abiJson = chainApi.abiBinToJson(
                AbiBinToJson(
                    "eosio.token",
                    "transfer",
                    abi
                )
            ).blockingGet()

            val body = abiJson.body()

            it("should return the bin") {
                assertTrue(abiJson.isSuccessful)
                assertNotNull(body)

                val jsonAdapter = Moshi.Builder()
                    .add(LocalDateTimeAdapter())
                    .build()
                    .adapter<TransferBody>(TransferBody::class.java)

                val responseTransferBody: TransferBody = jsonAdapter.fromJson(body!!.string())!!

                assertEquals(transferBody.args, responseTransferBody.args)
            }
        }
    }
})