package com.memtrip.eos.http.rpc.chain

import com.memtrip.eos.core.crypto.EosPrivateKey
import com.memtrip.eos.http.aggregation.AggregateContext
import com.memtrip.eos.http.aggregation.account.CreateAccountAggregate
import com.memtrip.eos.http.rpc.Api
import com.memtrip.eos.http.rpc.Config
import com.memtrip.eos.http.rpc.generateUniqueAccountName
import com.memtrip.eos.http.rpc.model.account.request.AccountName
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
class ChainGetAccountTest : Spek({

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

        on("v1/chain/get_account -> eosio.token") {

            val account = chainApi.getAccount(AccountName("eosio.token")).blockingGet()

            it("should return the account") {
                assertTrue(account.isSuccessful)
                assertNotNull(account.body())
            }
        }

        on("v1/chain/get_account -> new account") {

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

            val account = chainApi.getAccount(AccountName(accountName)).blockingGet()

            it("should return the account") {
                assertTrue(account.isSuccessful)
                assertNotNull(account.body())
            }
        }

        on("v1/chain/get_account -> voter info") {

            val account = chainApi.getAccount(AccountName("memtripadmin")).blockingGet()

            it("should return the account") {
                assertTrue(account.isSuccessful)
                assertNotNull(account.body()!!.voter_info)
                assertTrue(account.body()!!.voter_info!!.producers.isNotEmpty())
            }
        }

        on("v1/chain/get_account -> voter info proxied") {

            val account = chainApi.getAccount(AccountName("memtripissue")).blockingGet()

            it("should return the account") {
                assertTrue(account.isSuccessful)
                assertNotNull(account.body())
                assertTrue(account.body()!!.voter_info!!.proxy.isNotEmpty())
            }
        }
    }
})