package com.memtrip.eos.http.rpc.chain

import com.memtrip.eos.abi.writer.compression.CompressionType
import com.memtrip.eos.core.block.BlockIdDetails
import com.memtrip.eos.core.crypto.EosPrivateKey
import com.memtrip.eos.http.rpc.Api
import com.memtrip.eos.http.rpc.model.signing.GetRequiredKeysBody
import com.memtrip.eos.http.rpc.model.transaction.Action
import com.memtrip.eos.http.rpc.model.transaction.Transaction
import com.memtrip.eos.http.rpc.model.transaction.TransactionAuthorization
import com.memtrip.eos.http.rpc.utils.Config
import com.memtrip.eos.http.rpc.utils.testabi.AbiBinaryGenTransferWriter
import com.memtrip.eos.http.rpc.utils.testabi.TransferArgs
import com.memtrip.eos.http.rpc.utils.testabi.TransferBody
import com.memtrip.eos.http.rpc.utils.transactionDefaultExpiry

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
class ChainGetRequiredKeysTest : Spek({

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

        on("v1/chain/get_required_keys") {

            /**
             * Import memtripissue key
             */
            val privateKey = EosPrivateKey("5HvDsbgjH574GALj5gRcnscMfAGBQD9JSWn3sHFsD7bNrkqXqpr")

            /**
             * v1/chain/get_info
             */
            val info = chainApi.getInfo().blockingGet().body()!!

            val blockIdDetails = BlockIdDetails(info.head_block_id)

            val transferBody = TransferBody(
                TransferArgs(
                    "user",
                    "tester",
                    "25.0000 SYS",
                    "here is some coins!")
            )

            val abiBin = AbiBinaryGenTransferWriter(CompressionType.NONE).squishTransferBody(transferBody).toHex()

            val transaction = Transaction(
                transactionDefaultExpiry(),
                blockIdDetails.blockNum,
                blockIdDetails.blockPrefix,
                0,
                0,
                0,
                emptyList(),
                asList(Action(
                    "eosio",
                    "newaccount",
                    asList(TransactionAuthorization(
                        "memtripissue",
                        "active")),
                    abiBin
                )),
                emptyList(),
                emptyList(),
                emptyList())

            /**
             * v1/chain/get_required_keys
             */
            val requiredKeys = chainApi.getRequiredKeys(GetRequiredKeysBody(
                transaction,
                asList(privateKey.publicKey.toString())
            )).blockingGet()

            it("should return the required keys") {
                Assert.assertNotNull(requiredKeys.body())
                Assert.assertTrue(requiredKeys.isSuccessful)
            }
        }
    }
})