package com.memtrip.eos.chain.actions.transaction

import com.memtrip.eos.chain.actions.Config
import com.memtrip.eos.chain.actions.SetupTransactions
import com.memtrip.eos.chain.actions.generateUniqueAccountName

import com.memtrip.eos.chain.actions.transaction.vote.VoteChain
import com.memtrip.eos.chain.actions.transactionDefaultExpiry
import com.memtrip.eos.core.crypto.EosPrivateKey
import com.memtrip.eos.http.rpc.Api
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.util.Arrays.asList
import java.util.concurrent.TimeUnit

@RunWith(JUnitPlatform::class)
class VoteChainTest : Spek({

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

        on("v1/chain/push_transaction -> vote for producer") {

            /**
             * Create account
             */
            val firstAccountPrivateKey = EosPrivateKey()
            val firstAccountName = generateUniqueAccountName()
            val createAccount = setupTransactions.createAccount(firstAccountName, firstAccountPrivateKey).blockingGet()

            /**
             * Send money from the signature provider to the first account
             */
            val transfer = setupTransactions.transfer(firstAccountName).blockingGet()

            val vote = VoteChain(chainApi).vote(
                VoteChain.Args(
                    firstAccountName,
                    "",
                    asList("memtripblock")
                ),
                TransactionContext(
                    firstAccountName,
                    firstAccountPrivateKey,
                    transactionDefaultExpiry()
                )
            ).blockingGet()

            it("should return the transaction") {
                assertNotNull(createAccount.body)
                assertTrue(createAccount.isSuccessful)
                assertNotNull(transfer.body)
                assertTrue(transfer.isSuccessful)
                assertNotNull(vote.body)
                assertTrue(vote.isSuccessful)
            }
        }

        on("v1/chain/push_transaction -> vote for proxy") {

            /**
             * Create account
             */
            val firstAccountPrivateKey = EosPrivateKey()
            val firstAccountName = generateUniqueAccountName()
            val createAccount = setupTransactions.createAccount(firstAccountName, firstAccountPrivateKey).blockingGet()

            /**
             * Send money from the signature provider to the first account
             */
            val transfer = setupTransactions.transfer(firstAccountName).blockingGet()

            val vote = VoteChain(chainApi).vote(
                VoteChain.Args(
                    firstAccountName,
                    "memtripproxy",
                    emptyList()
                ),
                TransactionContext(
                    firstAccountName,
                    firstAccountPrivateKey,
                    transactionDefaultExpiry()
                )
            ).blockingGet()

            it("should return the transaction") {
                assertTrue(createAccount.isSuccessful)
                assertNotNull(createAccount.body)
                assertTrue(transfer.isSuccessful)
                assertNotNull(transfer.body)
                assertTrue(vote.isSuccessful)
                assertNotNull(vote.body)
            }
        }
    }
})