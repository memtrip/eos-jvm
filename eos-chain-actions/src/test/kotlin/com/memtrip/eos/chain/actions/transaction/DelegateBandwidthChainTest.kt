package com.memtrip.eos.chain.actions.transaction

import com.memtrip.eos.chain.actions.Config
import com.memtrip.eos.chain.actions.SetupTransactions
import com.memtrip.eos.chain.actions.generateUniqueAccountName
import com.memtrip.eos.chain.actions.transaction.account.CreateAccountChain
import com.memtrip.eos.chain.actions.transaction.account.DelegateBandwidthChain
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
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(JUnitPlatform::class)
class DelegateBandwidthChainTest : Spek({

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

        on("v1/chain/push_transaction -> delegate bandwidth") {

            /**
             * Create account
             */
            val newAccountName = generateUniqueAccountName()
            val newAccountPrivateKey = EosPrivateKey()
            val createAccount = setupTransactions.createAccount(newAccountName, newAccountPrivateKey).blockingGet()

            /**
             * Send money from memtripissue to the new account
             */
            val transfer = setupTransactions.transfer(newAccountName).blockingGet()

            /**
             * Delegate bandwidth
             */
            val delegateBw = DelegateBandwidthChain(chainApi).delegateBandwidth(
                DelegateBandwidthChain.Args(
                    newAccountName,
                    newAccountName,
                    "0.1000 EOS",
                    "0.1000 EOS",
                    false
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
                assertTrue(delegateBw.isSuccessful)
                assertNotNull(delegateBw.body)

                /**
                 * Verify the banwidth has increased
                 */
                val account = chainApi.getAccount(AccountName(newAccountName)).blockingGet()
                assertEquals("1.1000 EOS", account.body()!!.total_resources!!.cpu_weight)
                assertEquals("0.2000 EOS", account.body()!!.total_resources!!.net_weight)
            }
        }
    }
})