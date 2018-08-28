package com.memtrip.eos.http.rpc.history

import com.memtrip.eos.core.crypto.EosPrivateKey
import com.memtrip.eos.http.aggregation.account.CreateAccount
import com.memtrip.eos.http.rpc.Api
import com.memtrip.eos.http.rpc.Config
import com.memtrip.eos.http.rpc.generateUniqueAccountName

import com.memtrip.eos.http.rpc.model.history.request.GetKeyAccounts
import com.memtrip.eos.http.rpc.toLocalDateTime
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.util.Calendar
import java.util.concurrent.TimeUnit

@RunWith(JUnitPlatform::class)
class ChainGetKeyAccountsTest : Spek({

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

        on("v1/history/get_key_accounts") {

            val privateKey = EosPrivateKey("5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3")

            CreateAccount(chainApi).createAccount(
                CreateAccount.Args(
                    generateUniqueAccountName(),
                    CreateAccount.Args.Quantity(
                        "1.0000 SYS",
                        "1.0000 SYS",
                        "11.0000 SYS"),
                    privateKey.publicKey,
                    privateKey.publicKey,
                    "eosio",
                    privateKey,
                    Calendar.getInstance().toLocalDateTime())).blockingGet()

            val accounts = historyApi.getKeyAccounts(GetKeyAccounts(privateKey.publicKey.toString())).blockingGet()

            it("should return the account") {
                Assert.assertTrue(accounts.isSuccessful)
                Assert.assertNotNull(accounts.body())
                Assert.assertTrue(accounts.body()!!.account_names.isNotEmpty())
            }
        }
    }
})