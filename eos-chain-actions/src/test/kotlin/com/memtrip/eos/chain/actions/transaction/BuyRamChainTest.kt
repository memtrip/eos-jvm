package com.memtrip.eos.chain.actions.transaction

import com.memtrip.eos.chain.actions.Config
import com.memtrip.eos.chain.actions.SetupTransactions
import com.memtrip.eos.chain.actions.generateUniqueAccountName
import com.memtrip.eos.chain.actions.transaction.account.BuyRamChain
import com.memtrip.eos.chain.actions.transaction.account.CreateAccountChain
import com.memtrip.eos.chain.actions.transaction.transfer.TransferChain
import com.memtrip.eos.chain.actions.transactionDefaultExpiry
import com.memtrip.eos.core.crypto.EosPrivateKey
import com.memtrip.eos.http.rpc.Api
import com.memtrip.eos.http.rpc.model.account.request.AccountName
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
class BuyRamChainTest : Spek({

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

        on("v1/chain/push_transaction -> buy ram") {

            /**
             * Create account
             */
            val newAccountName = generateUniqueAccountName()
            val newAccountPrivateKey = EosPrivateKey()

            val createAccount = setupTransactions.createAccount(
                newAccountName,
                newAccountPrivateKey
            ).blockingGet()

            /**
             * Send money from memtripissue provider to the new account
             */
            val transfer = setupTransactions.transfer(newAccountName).blockingGet()

            /**
             * ram bytes before purchase
             */
            val beforeAccount = chainApi.getAccount(AccountName(newAccountName)).blockingGet()
            val ramBytesBefore = beforeAccount.body()!!.total_resources!!.ram_bytes

            /**
             * Buy ram
             */
            val buyRam = BuyRamChain(chainApi).buyRam(
                BuyRamChain.Args(
                    newAccountName,
                    "0.0900 EOS"
                ),
                TransactionContext(
                    newAccountName,
                    newAccountPrivateKey,
                    transactionDefaultExpiry()
                )
            ).blockingGet()

            it("should return the transaction") {
                assertTrue(createAccount.isSuccessful)
                assertNotNull(createAccount.body)
                assertTrue(transfer.isSuccessful)
                assertNotNull(transfer.body)
                assertTrue(buyRam.isSuccessful)
                assertNotNull(buyRam.body)

                /**
                 * Verify the banwidth has increased
                 */
                val afterAccount = chainApi.getAccount(AccountName(newAccountName)).blockingGet()
                val ramBytesAfter = afterAccount.body()!!.total_resources!!.ram_bytes

                assertTrue(ramBytesAfter > ramBytesBefore)
            }
        }
    }
})