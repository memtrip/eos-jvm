package com.memtrip.eos.http.aggregation

import com.memtrip.eos.core.crypto.EosPrivateKey
import com.memtrip.eos.http.aggregation.account.CreateAccountAggregate
import com.memtrip.eos.http.aggregation.transfer.TransferAggregate
import com.memtrip.eos.http.rpc.Api
import com.memtrip.eos.http.rpc.Config
import com.memtrip.eos.http.rpc.generateUniqueAccountName
import com.memtrip.eos.http.rpc.transactionDefaultExpiry
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(JUnitPlatform::class)
class TransferAggregateTest : Spek({

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

        on("v1/chain/push_transaction -> transfer") {

            val signatureProviderPrivateKey = EosPrivateKey("5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3")

            /**
             * First account
             */
            val firstAccountPrivateKey = EosPrivateKey()

            val firstAccountName = generateUniqueAccountName()
            CreateAccountAggregate(chainApi).createAccount(
                CreateAccountAggregate.Args(
                    firstAccountName,
                    CreateAccountAggregate.Args.Quantity(
                        "1.0000 SYS",
                        "1.0000 SYS",
                        "11.0000 SYS"),
                    CreateAccountAggregate.Args.Transfer(
                        "0.1000 SYS",
                        "memo"
                    ),
                    firstAccountPrivateKey.publicKey,
                    firstAccountPrivateKey.publicKey,
                    true
                ),
                AggregateContext(
                    "eosio",
                    signatureProviderPrivateKey,
                    transactionDefaultExpiry()
                )
            ).blockingGet()

            /**
             * Second account
             */
            val secondAccountPrivateKey = EosPrivateKey()

            val secondAccountName = generateUniqueAccountName()
            CreateAccountAggregate(chainApi).createAccount(
                CreateAccountAggregate.Args(
                    secondAccountName,
                    CreateAccountAggregate.Args.Quantity(
                        "1.0000 SYS",
                        "1.0000 SYS",
                        "11.0000 SYS"),
                    CreateAccountAggregate.Args.Transfer(
                        "0.1000 SYS",
                        "memo"
                    ),
                    secondAccountPrivateKey.publicKey,
                    secondAccountPrivateKey.publicKey,
                    true
                ),
                AggregateContext(
                    "eosio",
                    signatureProviderPrivateKey,
                    transactionDefaultExpiry()
                )
            ).blockingGet()

            /**
             * Send money from the signature provider to the first account
             */
            val transfer1 = TransferAggregate(chainApi).transfer(
                TransferAggregate.Args(
                    "eosio",
                    firstAccountName,
                    "100.0000 SYS",
                    "here is some coins!"
                ),
                AggregateContext(
                    "eosio",
                    signatureProviderPrivateKey,
                    transactionDefaultExpiry()
                )
            ).blockingGet()

            /**
             * Transfer money from the first account to the second account
             */
            val transfer2 = TransferAggregate(chainApi).transfer(
                TransferAggregate.Args(
                    firstAccountName,
                    secondAccountName,
                    "2.0000 SYS",
                    "Enjoy these coins!"
                ),
                AggregateContext(
                    firstAccountName,
                    firstAccountPrivateKey,
                    transactionDefaultExpiry()
                )
            ).blockingGet()

            /**
             * Transfer money from the second account to the first account
             */
            val transfer3 = TransferAggregate(chainApi).transfer(
                TransferAggregate.Args(
                    secondAccountName,
                    firstAccountName,
                    "2.0000 SYS",
                    "Enjoy these coins!"
                ),
                AggregateContext(
                    secondAccountName,
                    secondAccountPrivateKey,
                    transactionDefaultExpiry()
                )
            ).blockingGet()

            it("should return the transaction") {
                Assert.assertNotNull(transfer1.body)
                Assert.assertTrue(transfer1.isSuccessful)

                Assert.assertNotNull(transfer2.body)
                Assert.assertTrue(transfer2.isSuccessful)

                Assert.assertNotNull(transfer3.body)
                Assert.assertTrue(transfer3.isSuccessful)
            }
        }
    }
})