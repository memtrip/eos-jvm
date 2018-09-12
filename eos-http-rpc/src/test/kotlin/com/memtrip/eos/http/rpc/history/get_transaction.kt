package com.memtrip.eos.http.rpc.history

import com.memtrip.eos.http.rpc.Api
import com.memtrip.eos.http.rpc.model.history.request.GetActions
import com.memtrip.eos.http.rpc.model.history.request.GetTransaction
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
class HistoryGetTransactionsTest : Spek({

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

        /**
         * v1/history/get_actions
         */
        val actions = historyApi.getActions(GetActions(
            "eosio.token"
        )).blockingGet()

        val action = actions.body()!!.actions[0]

        on("v1/history/get_transactions") {

            val transaction = historyApi.getTransaction(GetTransaction(
                action.action_trace.trx_id
            )).blockingGet()

            it("should return the account") {
                assertTrue(transaction.isSuccessful)
                assertNotNull(transaction.body())
            }
        }
    }
})