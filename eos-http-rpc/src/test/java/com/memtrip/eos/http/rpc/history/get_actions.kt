package com.memtrip.eos.http.rpc.history

import com.memtrip.eos.core.crypto.EosPrivateKey
import com.memtrip.eos.http.aggregation.AggregateContext
import com.memtrip.eos.http.aggregation.account.CreateAccountAggregate
import com.memtrip.eos.http.aggregation.transfer.TransferAggregate
import com.memtrip.eos.http.rpc.Api
import com.memtrip.eos.http.rpc.Config
import com.memtrip.eos.http.rpc.generateUniqueAccountName
import com.memtrip.eos.http.rpc.model.history.request.GetActions
import com.memtrip.eos.http.rpc.transactionDefaultExpiry
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
        val chainApi by memoized { Api(Config.CHAIN_API_BASE_URL, okHttpClient).chain }

        val privateKey = EosPrivateKey("5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3")

        val accountName = generateUniqueAccountName()

        CreateAccountAggregate(chainApi).createAccount(
            CreateAccountAggregate.Args(
                accountName,
                CreateAccountAggregate.Args.Quantity(
                    "1.0000 SYS",
                    "1.0000 SYS",
                    "11.0000 SYS"),
                CreateAccountAggregate.Args.Transfer(
                    "0.1000 SYS",
                    "memo"
                ),
                privateKey.publicKey,
                privateKey.publicKey,
                true
            ),
            AggregateContext(
                "eosio",
                privateKey,
                transactionDefaultExpiry()
            )
        ).blockingGet()

        TransferAggregate(chainApi).transfer(
            TransferAggregate.Args(
                "eosio",
                accountName,
                "10.0000 SYS",
                "here is some coins!"
            ),
            AggregateContext(
                "eosio",
                privateKey,
                transactionDefaultExpiry()
            )
        ).blockingGet()

        on("v1/history/get_actions") {

            val accounts = historyApi.getActions(GetActions(
                "eosio.token"
            )).blockingGet()

            it("should return the account") {
                assertTrue(accounts.isSuccessful)
                assertNotNull(accounts.body())
                assertTrue(accounts.body()!!.actions.isNotEmpty())
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