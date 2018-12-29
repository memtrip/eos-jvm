package com.memtrip.eos.chain.actions.query

import com.memtrip.eos.chain.actions.Config
import com.memtrip.eos.chain.actions.query.ramprice.GetRamPrice
import com.memtrip.eos.http.rpc.Api
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertTrue
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(JUnitPlatform::class)
class CalculateRamPriceTest : Spek({

    given("an Api") {

        val okHttpClient by memoized {
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build()
        }

        val chainApi by memoized { Api(Config.MAINNET_API_BASE_URL, okHttpClient).chain }

        on("get ram price per byte") {

            val response = GetRamPrice(chainApi).getPricePerKilobyte().blockingGet()

            it("should return a ram price per byte") {
                assertTrue(response > 0)
            }
        }
    }
})