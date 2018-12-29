package com.memtrip.eos.chain.actions.transaction

import com.memtrip.eos.chain.actions.Config
import com.memtrip.eos.chain.actions.generateUniqueAccountName
import com.memtrip.eos.chain.actions.transaction.account.CreateAccountChain
import com.memtrip.eos.chain.actions.transactionDefaultExpiry
import com.memtrip.eos.core.crypto.EosPrivateKey
import com.memtrip.eos.http.rpc.Api
import com.memtrip.eos.http.rpc.model.history.request.GetKeyAccounts
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
class CreateAccountChainTest : Spek({

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
        val historyApi by memoized { Api(Config.CHAIN_API_BASE_URL, okHttpClient).history }

        on("v1/chain/push_transaction -> create account") {

            val memtripissuePrivateKey = EosPrivateKey("5HvDsbgjH574GALj5gRcnscMfAGBQD9JSWn3sHFsD7bNrkqXqpr")

            /**
             * New account
             */
            val newAccountName = generateUniqueAccountName()
            val newAccountPrivateKey = EosPrivateKey()

            val response = CreateAccountChain(chainApi).createAccount(
                CreateAccountChain.Args(
                    newAccountName,
                    CreateAccountChain.Args.Quantity(
                    3048,
                    "0.1000 EOS",
                    "1.0000 EOS"),
                    newAccountPrivateKey.publicKey,
                    newAccountPrivateKey.publicKey,
                    true
                ),
                TransactionContext(
                    "memtripissue",
                    memtripissuePrivateKey,
                    transactionDefaultExpiry()
                )
            ).blockingGet()

            /**
             * Get account by public key
             */
            val accounts = historyApi.getKeyAccounts(GetKeyAccounts(newAccountPrivateKey.publicKey.toString())).blockingGet()

            it("should return the transaction") {
                assertTrue(response.isSuccessful)
                assertNotNull(response.body)

                assertTrue(accounts.isSuccessful)
                assertNotNull(accounts.body())
                assertTrue(accounts.body()!!.account_names.size == 1)
            }
        }
    }
})