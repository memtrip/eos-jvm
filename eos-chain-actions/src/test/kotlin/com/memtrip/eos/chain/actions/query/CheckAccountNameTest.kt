package com.memtrip.eos.chain.actions.query

import com.memtrip.eos.chain.actions.Config
import com.memtrip.eos.chain.actions.SetupTransactions
import com.memtrip.eos.chain.actions.generateUniqueAccountName
import com.memtrip.eos.chain.actions.query.accountname.CheckAccountNameExists
import com.memtrip.eos.chain.actions.transaction.TransactionContext
import com.memtrip.eos.chain.actions.transaction.account.CreateAccountChain
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
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(JUnitPlatform::class)
class CheckAccountNameTest : Spek({

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

        on("v1/chain/get_currency_balance -> Account exists") {

            val privateKey = EosPrivateKey("5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3")

            val firstAccountName = generateUniqueAccountName()
            val firstAccountPrivateKey = EosPrivateKey()
            val createAccount = setupTransactions.createAccount(firstAccountName, firstAccountPrivateKey).blockingGet()

            val accountExists = CheckAccountNameExists(chainApi)
                .checkAccountNameExists(firstAccountName)
                .blockingGet()

            it("should return true") {
                assertTrue(createAccount.isSuccessful)
                assertTrue(accountExists)
            }
        }

        on("v1/chain/get_currency_balance -> Account does not exists") {

            val accountExists = CheckAccountNameExists(chainApi)
                .checkAccountNameExists(generateUniqueAccountName())
                .blockingGet()

            it("should return false") {
                assertFalse(accountExists)
            }
        }
    }
})