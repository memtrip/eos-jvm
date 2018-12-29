package com.memtrip.eos.chain.actions.query

import com.memtrip.eos.chain.actions.Config
import com.memtrip.eos.chain.actions.query.proxy.GetRegProxyInfo
import com.memtrip.eos.http.rpc.Api
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(JUnitPlatform::class)
class GetRegProxyInfoTest : Spek({

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

        on("v1/chain/get_table_rows -> proxies") {

            val page1Response = GetRegProxyInfo(chainApi).getProxies(100).blockingGet()
            val page2Response = GetRegProxyInfo(chainApi).getProxies(100, page1Response.last().owner).blockingGet()

            it("should return the proxies") {
                page2Response.map { proxyJson ->
                    assertFalse(page1Response.contains(proxyJson))
                }
            }
        }

        on("v1/chain/get_table_rows -> single proxy") {

            val proxyInfo = GetRegProxyInfo(chainApi).getProxy("amazinggamer").blockingGet()

            it("should return the proxies") {
                assertNotNull(proxyInfo)
                assertEquals("amazinggamer", proxyInfo.owner)
            }
        }
    }
})