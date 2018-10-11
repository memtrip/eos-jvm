package com.memtrip.eos.http.rpc.wallet

import com.memtrip.eos.abi.writer.compression.CompressionType
import com.memtrip.eos.core.block.BlockIdDetails
import com.memtrip.eos.core.crypto.EosPrivateKey
import com.memtrip.eos.http.rpc.Api
import com.memtrip.eos.http.rpc.model.transaction.Action
import com.memtrip.eos.http.rpc.model.transaction.Transaction
import com.memtrip.eos.http.rpc.model.transaction.TransactionAuthorization
import com.memtrip.eos.http.rpc.utils.Config
import com.memtrip.eos.http.rpc.utils.DateAdapter
import com.memtrip.eos.http.rpc.utils.testabi.AbiBinaryGenTransferWriter
import com.memtrip.eos.http.rpc.utils.testabi.TransferArgs
import com.memtrip.eos.http.rpc.utils.testabi.TransferBody
import com.memtrip.eos.http.rpc.utils.transactionDefaultExpiry

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
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.util.Arrays.asList
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
             * signature-provider key
             */
            val privateKey = EosPrivateKey("5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3")

            /**
             * v1/chain/get_info
             */
            val info = chainApi.getInfo().blockingGet().body()!!

            val blockIdDetails = BlockIdDetails(info.head_block_id)

            val transferBody = TransferBody(
                "eosio.token",
                "transfer",
                TransferArgs(
                    "eosio.token",
                    "tester",
                    "1.0000 SYS",
                    "here is some coins!")
            )

            val abiBin = AbiBinaryGenTransferWriter(CompressionType.NONE).squishTransferBody(transferBody).toHex()

            val transaction = Transaction(
                transactionDefaultExpiry(),
                blockIdDetails.blockNum,
                blockIdDetails.blockPrefix,
                0,
                0,
                120000,
                emptyList(),
                asList(Action(
                    "eosio.token",
                    "transfer",
                    asList(TransactionAuthorization(
                        "eosio",
                        "active")),
                    abiBin
                )),
                emptyList(),
                emptyList(),
                emptyList())

            val transactionJsonAdapter = Moshi.Builder()
                .add(DateAdapter())
                .build()
                .adapter<Transaction>(Transaction::class.java)

            /**
             * v1/wallet/sign_transaction
             */
            val jsonArray = JSONArray()
            jsonArray.put(transactionJsonAdapter.toJsonValue(transaction))
            jsonArray.put(with(JSONArray()) { put(privateKey.publicKey.toString()) })
            jsonArray.put(info.chain_id)

            val signedTransaction = walletApi.signTransaction(
                RequestBody.create(MediaType.parse("application/json"), jsonArray.toString())
            ).blockingGet()

            it("should return a transaction receipt") {
                assertNotNull(signedTransaction.body()!!.signatures)
                assertTrue(signedTransaction.body()!!.signatures.isNotEmpty())
                assertTrue(signedTransaction.isSuccessful)
            }
        }
    }
})