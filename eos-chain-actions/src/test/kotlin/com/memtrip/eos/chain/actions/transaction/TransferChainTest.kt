package com.memtrip.eos.chain.actions.transaction

import com.memtrip.eos.chain.actions.Config
import com.memtrip.eos.chain.actions.SetupTransactions
import com.memtrip.eos.chain.actions.generateUniqueAccountName

import com.memtrip.eos.chain.actions.transaction.transfer.TransferChain
import com.memtrip.eos.chain.actions.transactionDefaultExpiry
import com.memtrip.eos.core.crypto.EosPrivateKey
import com.memtrip.eos.http.rpc.Api
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(JUnitPlatform::class)
class TransferChainTest : Spek({

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

        val setupTransactions by memoized { SetupTransactions(chainApi) }

        on("v1/chain/push_transaction -> transfer") {

            val signatureProviderPrivateKey = EosPrivateKey("5HvDsbgjH574GALj5gRcnscMfAGBQD9JSWn3sHFsD7bNrkqXqpr")

            /**
             * First account
             */
            val firstAccountPrivateKey = EosPrivateKey()
            val firstAccountName = generateUniqueAccountName()
            setupTransactions.createAccount(firstAccountName, firstAccountPrivateKey).blockingGet()

            /**
             * Second account
             */
            val secondAccountPrivateKey = EosPrivateKey()
            val secondAccountName = generateUniqueAccountName()
            setupTransactions.createAccount(secondAccountName, secondAccountPrivateKey).blockingGet()

            /**
             * Send money from the signature provider to the first account
             */
            val transfer1 = TransferChain(chainApi).transfer(
                "eosio.token",
                TransferChain.Args(
                    "memtripissue",
                    firstAccountName,
                    "0.0001 EOS",
                    "eos-swift test suite -> transfer тест"
                ),
                TransactionContext(
                    "memtripissue",
                    signatureProviderPrivateKey,
                    transactionDefaultExpiry()
                )
            ).blockingGet()

            /**
             * Transfer money from the first account to the second account
             */
            val transfer2 = TransferChain(chainApi).transfer(
                "eosio.token",
                TransferChain.Args(
                    firstAccountName,
                    secondAccountName,
                    "0.0001 EOS",
                    "eos-swift test suite -> transfer 轮子把巨人挤出局"
                ),
                TransactionContext(
                    firstAccountName,
                    firstAccountPrivateKey,
                    transactionDefaultExpiry()
                )
            ).blockingGet()

            /**
             * Transfer money from the second account to the first account
             */
            val transfer3 = TransferChain(chainApi).transfer(
                "eosio.token",
                TransferChain.Args(
                    secondAccountName,
                    firstAccountName,
                    "0.0001 EOS",
                    "eos-swift test suite -> тестируем testing 1234567890"
                ),
                TransactionContext(
                    secondAccountName,
                    secondAccountPrivateKey,
                    transactionDefaultExpiry()
                )
            ).blockingGet()

            it("should return the transaction") {

                // Transfer 1
                assertTrue(transfer1.isSuccessful)
                assertNotNull(transfer1.body)

                val data1 = transfer1.body!!.processed.action_traces[0].act.data as Map<*, *>
                assertEquals(
                        "eos-swift test suite -> transfer тест",
                        data1["memo"])

                // Transfer 2
                assertTrue(transfer2.isSuccessful)
                assertNotNull(transfer2.body)

                val data2 = transfer2.body!!.processed.action_traces[0].act.data as Map<*, *>
                assertEquals(
                        "eos-swift test suite -> transfer 轮子把巨人挤出局",
                        data2["memo"])

                // Transfer 3
                assertTrue(transfer3.isSuccessful)
                assertNotNull(transfer3.body)

                val data3 = transfer3.body!!.processed.action_traces[0].act.data as Map<*, *>
                assertEquals(
                        "eos-swift test suite -> тестируем testing 1234567890",
                        data3["memo"])
            }
        }
    }
})