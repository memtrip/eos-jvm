package com.memtrip.eos.chain.actions.query

import com.memtrip.eos.chain.actions.Config
import com.memtrip.eos.chain.actions.query.bandwidth.GetDelegatedBandwidth
import com.memtrip.eos.http.rpc.Api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(JUnitPlatform::class)
class GetDelegatedBandwidthTest : Spek({

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

        on("v1/chain/get_table_rows -> bandwidth") {

            val response = GetDelegatedBandwidth(chainApi).getBandwidth("memtripissue").blockingGet()

            it("should return the delegated bandwidth") {
                assertEquals(2, response.size)
                assertEquals(response[0].from, "memtripissue")
                assertEquals(response[0].to, "memtripissue")
                assertEquals(response[0].net_weight, "200100.0000 SYS")
                assertEquals(response[0].cpu_weight, "200100.0000 SYS")
                assertEquals(response[1].from, "memtripissue")
                assertEquals(response[1].to, "memtripproxy")
                assertEquals(response[1].net_weight, "100000.0000 SYS")
                assertEquals(response[1].cpu_weight, "100000.0000 SYS")
            }
        }
    }
})