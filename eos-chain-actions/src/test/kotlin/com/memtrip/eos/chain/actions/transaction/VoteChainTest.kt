package com.memtrip.eos.chain.actions.transaction

import com.memtrip.eos.chain.actions.Config
import com.memtrip.eos.chain.actions.generateUniqueAccountName
import com.memtrip.eos.chain.actions.transaction.account.CreateAccountChain
import com.memtrip.eos.chain.actions.transaction.account.DelegateBandwidthChain
import com.memtrip.eos.chain.actions.transaction.transfer.TransferChain
import com.memtrip.eos.chain.actions.transaction.vote.VoteChain
import com.memtrip.eos.chain.actions.transactionDefaultExpiry
import com.memtrip.eos.core.crypto.EosPrivateKey
import com.memtrip.eos.http.rpc.Api
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.util.asList
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

        on("v1/chain/push_transaction -> vote for producer") {

            val signatureProviderPrivateKey = EosPrivateKey("5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3")

            /**
             * First account
             */
            val firstAccountPrivateKey = EosPrivateKey()

            val firstAccountName = generateUniqueAccountName()
            CreateAccountChain(chainApi).createAccount(
                CreateAccountChain.Args(
                    firstAccountName,
                    CreateAccountChain.Args.Quantity(
                        6096,
                        "1.0000 SYS",
                        "11.0000 SYS"),
                    firstAccountPrivateKey.publicKey,
                    firstAccountPrivateKey.publicKey,
                    true
                ),
                TransactionContext(
                    "eosio",
                    signatureProviderPrivateKey,
                    transactionDefaultExpiry()
                )
            ).blockingGet()

            /**
             * Send money from the signature provider to the first account
             */
            val transfer = TransferChain(chainApi).transfer(
                TransferChain.Args(
                    "eosio",
                    firstAccountName,
                    "100.0000 SYS",
                    "here is some coins!"
                ),
                TransactionContext(
                    "eosio",
                    signatureProviderPrivateKey,
                    transactionDefaultExpiry()
                )
            ).blockingGet()

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
            CreateAccountChain(chainApi).createAccount(
                CreateAccountChain.Args(
                    firstAccountName,
                    CreateAccountChain.Args.Quantity(
                        6096,
                        "1.0000 SYS",
                        "1.0000 SYS"),
                    firstAccountPrivateKey.publicKey,
                    firstAccountPrivateKey.publicKey,
                    true
                ),
                TransactionContext(
                    "eosio",
                    signatureProviderPrivateKey,
                    transactionDefaultExpiry()
                )
            ).blockingGet()

            /**
             * Send money from the signature provider to the first account
             */
            val transfer = TransferChain(chainApi).transfer(
                TransferChain.Args(
                    "eosio",
                    firstAccountName,
                    "10.0000 SYS",
                    "here is some coins!"
                ),
                TransactionContext(
                    "eosio",
                    signatureProviderPrivateKey,
                    transactionDefaultExpiry()
                )
            ).blockingGet()

            val delegateBw = DelegateBandwidthChain(chainApi).delegateBandwidth(
                DelegateBandwidthChain.Args(
                    firstAccountName,
                    firstAccountName,
                    "1.0000 SYS",
                    "1.0000 SYS",
                    false
                ),
                TransactionContext(
                    firstAccountName,
                    firstAccountPrivateKey,
                    transactionDefaultExpiry()
                )
            ).blockingGet()

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
                Assert.assertTrue(transfer.isSuccessful)
                Assert.assertNotNull(transfer.body)

                Assert.assertTrue(delegateBw.isSuccessful)
                Assert.assertNotNull(delegateBw.body)

                Assert.assertTrue(vote.isSuccessful)
                Assert.assertNotNull(vote.body)
            }
        }
    }
})