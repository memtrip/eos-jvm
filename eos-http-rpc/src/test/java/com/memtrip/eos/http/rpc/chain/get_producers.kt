package com.memtrip.eos.http.rpc.chain

import com.memtrip.eos.http.rpc.Api
import com.memtrip.eos.http.rpc.model.producer.request.GetProducers
import com.memtrip.eos.http.rpc.utils.Config
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(JUnitPlatform::class)
class ChainGetProducerTest : Spek({

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

        on("v1/chain/get_producers") {

            val producers = chainApi.getProducers(
                GetProducers(
                    true,
                    "",
                    10
                )
            ).blockingGet()

            it("should return a list of producers") {
                Assert.assertTrue(producers.isSuccessful)
                Assert.assertNotNull(producers.body())
            }
        }
    }
})