package com.memtrip.eos.chain.actions.query

import com.memtrip.eos.chain.actions.Config
import com.memtrip.eos.chain.actions.SetupTransactions
import com.memtrip.eos.chain.actions.generateUniqueAccountName
import com.memtrip.eos.chain.actions.query.bandwidth.GetDelegatedBandwidth
import com.memtrip.eos.chain.actions.transaction.TransactionContext
import com.memtrip.eos.chain.actions.transaction.account.DelegateBandwidthChain
import com.memtrip.eos.chain.actions.transactionDefaultExpiry
import com.memtrip.eos.core.crypto.EosPrivateKey
import com.memtrip.eos.http.rpc.Api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(JUnitPlatform::class)
class GetDelegatedBandwidthTest : Spek({

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

        on("v1/chain/get_table_rows -> bandwidth") {

            val firstAccountName = generateUniqueAccountName()
            val firstAccountPrivateKey = EosPrivateKey()
            val firstAccountCreate = setupTransactions.createAccount(firstAccountName, firstAccountPrivateKey).blockingGet()
            val firstAccountTransfer = setupTransactions.transfer(firstAccountName).blockingGet()

            val secondAccountName = generateUniqueAccountName()
            val secondAccountPrivateKey = EosPrivateKey()
            val secondAccountCreate = setupTransactions.createAccount(secondAccountName, secondAccountPrivateKey).blockingGet()
            val secondAccountTransfer = setupTransactions.transfer(secondAccountName).blockingGet()

            /* delegate first to self */
            val firstDelegateBw = DelegateBandwidthChain(chainApi).delegateBandwidth(
                DelegateBandwidthChain.Args(
                    firstAccountName,
                    firstAccountName,
                    "0.0001 EOS",
                    "0.0001 EOS",
                    false
                ),
                TransactionContext(
                    firstAccountName,
                    firstAccountPrivateKey,
                    transactionDefaultExpiry()
                )
            ).blockingGet()

            /* delegate first to second */
            val secondDelegateBw = DelegateBandwidthChain(chainApi).delegateBandwidth(
                DelegateBandwidthChain.Args(
                    firstAccountName,
                    secondAccountName,
                    "0.0001 EOS",
                    "0.0001 EOS",
                    false
                ),
                TransactionContext(
                    firstAccountName,
                    firstAccountPrivateKey,
                    transactionDefaultExpiry()
                )
            ).blockingGet()

            val response = GetDelegatedBandwidth(chainApi).getBandwidth(firstAccountName).blockingGet()

            it("should return the delegated bandwidth") {
                assertTrue(firstAccountCreate.isSuccessful)
                assertTrue(firstAccountTransfer.isSuccessful)
                assertTrue(secondAccountCreate.isSuccessful)
                assertTrue(secondAccountTransfer.isSuccessful)
                assertTrue(firstDelegateBw.isSuccessful)
                assertTrue(secondDelegateBw.isSuccessful)

                assertEquals(2, response.size)

                assertEquals(response[0].from, firstAccountName)
                assertEquals(response[0].to, secondAccountName)
                assertEquals(response[0].net_weight, "0.0001 EOS")
                assertEquals(response[0].cpu_weight, "0.0001 EOS")
                assertEquals(response[1].from, firstAccountName)
                assertEquals(response[1].to, firstAccountName)
                assertEquals(response[1].net_weight, "0.1001 EOS")
                assertEquals(response[1].cpu_weight, "1.0001 EOS")
            }
        }
    }
})