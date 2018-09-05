package com.memtrip.eos.http.rpc.wallet

import com.memtrip.eos.abi.writer.compression.CompressionType
import com.memtrip.eos.core.block.BlockIdDetails
import com.memtrip.eos.core.crypto.EosPrivateKey
import com.memtrip.eos.http.aggregation.account.actions.buyram.BuyRamArgs
import com.memtrip.eos.http.aggregation.account.actions.buyram.BuyRamBody
import com.memtrip.eos.http.aggregation.account.actions.delegatebw.DelegateBandwidthArgs
import com.memtrip.eos.http.aggregation.account.actions.delegatebw.DelegateBandwidthBody
import com.memtrip.eos.http.aggregation.account.actions.newaccount.NewAccountArgs
import com.memtrip.eos.http.aggregation.account.actions.newaccount.NewAccountBody
import com.memtrip.eos.http.rpc.Api
import com.memtrip.eos.http.rpc.Config
import com.memtrip.eos.http.rpc.generateUniqueAccountName
import com.memtrip.eos.http.rpc.model.account.response.AccountKey
import com.memtrip.eos.http.rpc.model.account.response.AccountRequiredAuth
import com.memtrip.eos.http.rpc.model.signing.PushTransaction
import com.memtrip.eos.http.rpc.model.transaction.TransactionAuthorization
import com.memtrip.eos.http.rpc.model.transaction.request.Action
import com.memtrip.eos.http.rpc.model.transaction.request.Transaction
import com.memtrip.eos.http.rpc.toLocalDateTime
import com.memtrip.eos.http.rpc.utils.LocalDateTimeAdapter
import com.memtrip.eosio.abi.binary.gen.AbiBinaryGen
import com.squareup.moshi.Moshi
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.json.JSONArray
import org.junit.Assert
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.util.Arrays
import java.util.Calendar
import java.util.concurrent.TimeUnit

/**
 * NOTE: The default wallet must contain the signature-provider key and the wallet must be unlocked
 * for this test to pass.
 */
@RunWith(JUnitPlatform::class)
class WalletSignTransactionTest : Spek({

    given("an Api.Wallet") {

        val okHttpClient by memoized {
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build()
        }

        val chainApi by memoized { Api(Config.CHAIN_API_BASE_URL, okHttpClient).chain }
        val walletApi by memoized { Api(Config.WALLET_API_BASE_URL, okHttpClient).wallet }

        on("v1/wallet/sign_transaction") {

            /**
             * New account name
             */
            val newAccountName = generateUniqueAccountName()

            /**
             * signature-provider key
             */
            val privateKey = EosPrivateKey("5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3")
            Assert.assertEquals(privateKey.publicKey.toString(), "EOS6MRyAjQq8ud7hVNYcfnVPJqcVpscN5So8BhtHuGYqET5GDW5CV")

            /**
             * v1/chain/get_info
             */
            val info = chainApi.getInfo().blockingGet().body()!!

            val blockIdDetails = BlockIdDetails(info.head_block_id)

            /**
             * Create account action
             */
            val createAccountBody = NewAccountBody(
                NewAccountArgs(
                    "eosio",
                    newAccountName,
                    AccountRequiredAuth(
                        1,
                        Arrays.asList(
                            AccountKey(
                                privateKey.publicKey.toString(),
                                1)
                        ),
                        emptyList(),
                        emptyList()
                    ),
                    AccountRequiredAuth(
                        1,
                        Arrays.asList(
                            AccountKey(
                                privateKey.publicKey.toString(),
                                1)
                        ),
                        emptyList(),
                        emptyList()
                    )
                )
            )

            val createAccountBin = AbiBinaryGen(CompressionType.NONE).squishNewAccountBody(createAccountBody).toHex()

            /**
             * Buy RAM action
             */
            val buyRamBody = BuyRamBody(
                BuyRamArgs(
                    "eosio",
                    newAccountName,
                    "1.0000 SYS")
            )

            val buyRamBin = AbiBinaryGen(CompressionType.NONE).squishBuyRamBody(buyRamBody).toHex()

            /**
             * Delegate bandwidth
             */
            val delegateBandwidthBody = DelegateBandwidthBody(
                "eosio",
                "delegatebw",
                DelegateBandwidthArgs(
                    "eosio",
                    newAccountName,
                    "1.0000 SYS",
                    "11.0000 SYS",
                    0
                )
            )

            val delegateBandWidthBin = AbiBinaryGen(CompressionType.NONE).squishDelegateBandwidthBody(delegateBandwidthBody).toHex()

            /**
             * Sign transaction
             */
            val transaction = Transaction(
                Calendar.getInstance().toLocalDateTime(),
                blockIdDetails.blockNum,
                blockIdDetails.blockPrefix,
                0,
                0,
                0,
                emptyList(),
                Arrays.asList(
                    Action(
                        "eosio",
                        "newaccount",
                        Arrays.asList(TransactionAuthorization(
                            "eosio",
                            "active")
                        ),
                        createAccountBin
                    ),
                    Action(
                        "eosio",
                        "buyram",
                        Arrays.asList(TransactionAuthorization(
                            "eosio",
                            "active")
                        ),
                        buyRamBin
                    ),
                    Action(
                        "eosio",
                        "delegatebw",
                        Arrays.asList(TransactionAuthorization(
                            "eosio",
                            "active")
                        ),
                        delegateBandWidthBin
                    )
                ),
                emptyList(),
                emptyList(),
                emptyList())

            /**
             * v1/wallet/sign_transaction
             */
            val jsonAdapter = Moshi.Builder()
                .add(LocalDateTimeAdapter())
                .build()
                .adapter<Transaction>(Transaction::class.java)

            val jsonArray = JSONArray()
            jsonArray.put(jsonAdapter.toJsonValue(transaction))
            jsonArray.put(with(JSONArray()) { put(privateKey.publicKey.toString()) })
            jsonArray.put(info.chain_id)

            val signedTransaction = walletApi.signTransaction(
                RequestBody.create(MediaType.parse("application/json"), jsonArray.toString())
            ).blockingGet()

            /**
             * v1/chain/push_transaction
             */
            val pushTransaction = chainApi.pushTransaction(
                PushTransaction(
                    signedTransaction.body()!!.signatures,
                    "none",
                    "",
                    AbiBinaryGen(CompressionType.NONE).squishTransaction(transaction).toHex()
                )
            ).blockingGet()

            it("should return a transaction receipt") {
                assertNotNull(pushTransaction.body())
                assertTrue(pushTransaction.isSuccessful)
            }
        }
    }
})