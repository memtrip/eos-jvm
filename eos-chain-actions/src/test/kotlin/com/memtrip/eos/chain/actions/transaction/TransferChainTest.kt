package com.memtrip.eos.chain.actions.transaction

import com.memtrip.eos.chain.actions.Config
import com.memtrip.eos.chain.actions.generateUniqueAccountName
import com.memtrip.eos.chain.actions.transaction.account.CreateAccountChain
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
import org.junit.Assert
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

        on("v1/chain/push_transaction -> transfer") {

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
                        14096,
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
             * Second account
             */
            val secondAccountPrivateKey = EosPrivateKey()

            val secondAccountName = generateUniqueAccountName()
            CreateAccountChain(chainApi).createAccount(
                CreateAccountChain.Args(
                    secondAccountName,
                    CreateAccountChain.Args.Quantity(
                        14096,
                        "1.0000 SYS",
                        "1.0000 SYS"),
                    secondAccountPrivateKey.publicKey,
                    secondAccountPrivateKey.publicKey,
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
            val transfer1 = TransferChain(chainApi).transfer(
                "eosio.token",
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

            /**
             * Transfer money from the first account to the second account
             */
            val transfer2 = TransferChain(chainApi).transfer(
                "eosio.token",
                TransferChain.Args(
                    firstAccountName,
                    secondAccountName,
                    "2.0000 SYS",
                    "Enjoy these coins!"
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
                    "2.0000 SYS",
                    "Enjoy these coins!"
                ),
                TransactionContext(
                    secondAccountName,
                    secondAccountPrivateKey,
                    transactionDefaultExpiry()
                )
            ).blockingGet()

            it("should return the transaction") {
                Assert.assertTrue(transfer1.isSuccessful)
                Assert.assertNotNull(transfer1.body)

                Assert.assertTrue(transfer2.isSuccessful)
                Assert.assertNotNull(transfer2.body)

                Assert.assertTrue(transfer3.isSuccessful)
                Assert.assertNotNull(transfer3.body)
            }
        }
    }
})