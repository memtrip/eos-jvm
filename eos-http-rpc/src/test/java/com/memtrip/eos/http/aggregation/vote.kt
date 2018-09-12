package com.memtrip.eos.http.aggregation

import com.memtrip.eos.core.crypto.EosPrivateKey
import com.memtrip.eos.http.aggregation.account.CreateAccountAggregate
import com.memtrip.eos.http.aggregation.account.DelegateBandwidthAggregate
import com.memtrip.eos.http.aggregation.transfer.TransferAggregate
import com.memtrip.eos.http.aggregation.vote.VoteAggregate
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
import java.util.Arrays.asList
import java.util.concurrent.TimeUnit

@RunWith(JUnitPlatform::class)
class VoteAggregateTest : Spek({

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

        on("v1/chain/push_transaction -> vote for producer") {

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
             * Send money from the signature provider to the first account
             */
            val transfer = TransferAggregate(chainApi).transfer(
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

            val vote = VoteAggregate(chainApi).vote(
                VoteAggregate.Args(
                    firstAccountName,
                    "",
                    asList("memtripblock")
                ),
                AggregateContext(
                    firstAccountName,
                    firstAccountPrivateKey,
                    transactionDefaultExpiry()
                )
            ).blockingGet()

            it("should return the transaction") {
                Assert.assertNotNull(transfer.body)
                Assert.assertTrue(transfer.isSuccessful)
                Assert.assertNotNull(vote.body)
                Assert.assertTrue(vote.isSuccessful)
            }
        }

        on("v1/chain/push_transaction -> vote for proxy") {

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
                    false
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
            val transfer = TransferAggregate(chainApi).transfer(
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

            val delegateBw = DelegateBandwidthAggregate(chainApi).delegateBandwidth(
                DelegateBandwidthAggregate.Args(
                    firstAccountName,
                    firstAccountName,
                    "1.0000 SYS",
                    "1.0000 SYS",
                    false
                ),
                AggregateContext(
                    firstAccountName,
                    firstAccountPrivateKey,
                    transactionDefaultExpiry()
                )
            ).blockingGet()

            val vote = VoteAggregate(chainApi).vote(
                VoteAggregate.Args(
                    firstAccountName,
                    "memtripadmin",
                    emptyList()
                ),
                AggregateContext(
                    firstAccountName,
                    firstAccountPrivateKey,
                    transactionDefaultExpiry()
                )
            ).blockingGet()

            it("should return the transaction") {
                Assert.assertNotNull(transfer.body)
                Assert.assertTrue(transfer.isSuccessful)
                Assert.assertNotNull(delegateBw.body)
                Assert.assertTrue(delegateBw.isSuccessful)
                Assert.assertNotNull(vote.body)
                Assert.assertTrue(vote.isSuccessful)
            }
        }
    }
})