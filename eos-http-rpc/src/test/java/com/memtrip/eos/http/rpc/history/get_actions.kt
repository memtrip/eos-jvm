package com.memtrip.eos.http.rpc.history

import com.memtrip.eos.http.rpc.Api
import com.memtrip.eos.http.rpc.model.history.request.GetActions
import com.memtrip.eos.http.rpc.utils.Config
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(JUnitPlatform::class)
class HistoryGetActionsTest : Spek({

    given("an Api") {

        val okHttpClient by memoized {
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build()
        }

        val historyApi by memoized { Api(Config.CHAIN_API_BASE_URL, okHttpClient).history }

        on("v1/history/get_actions") {

            val actions = historyApi.getActions(GetActions(
                "eosio.token"
            )).blockingGet()

            it("should return the account") {
                assertTrue(actions.isSuccessful)
                assertNotNull(actions.body())
                assertTrue(actions.body()!!.actions.isNotEmpty())
            }
        }

        on("v1/history/get_actions with pos 100") {

            val actions = historyApi.getActions(GetActions(
                "eosio.token",
                100000
            )).blockingGet()

            it("should return the account") {
                assertTrue(actions.isSuccessful)
                assertNotNull(actions.body())
                assertTrue(actions.body()!!.actions.isEmpty())
            }
        }
    }
})