package com.memtrip.eos.http.rpc.wallet

import com.memtrip.eos.abi.writer.compression.CompressionType

import com.memtrip.eos.core.crypto.EosPrivateKey

import com.memtrip.eos.http.rpc.Api
import com.memtrip.eos.http.rpc.utils.Config
import com.memtrip.eos.http.rpc.utils.generateUniqueWalletName
import com.memtrip.eos.http.rpc.utils.testabi.AbiBinaryGenTransferWriter
import com.memtrip.eos.http.rpc.utils.testabi.TransferArgs
import com.memtrip.eos.http.rpc.utils.testabi.TransferBody

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
import java.util.Arrays.asList
import java.util.concurrent.TimeUnit

@RunWith(JUnitPlatform::class)
class WalletSignDigestTest : Spek({

    given("an Api.Wallet") {

        val okHttpClient by memoized {
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build()
        }

        val walletApi by memoized { Api(Config.WALLET_API_BASE_URL, okHttpClient).wallet }

        on("v1/wallet/sign_digest") {

            /**
             * Local key
             */
            val privateKey = EosPrivateKey("5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3")

            /**
             * Create wallet and import eos private key
             */
            val walletName = generateUniqueWalletName()

            walletApi.create(walletName).blockingGet()

            walletApi
                .importKey(listOf(walletName, privateKey.toString()))
                .blockingGet()

            /**
             * v1/wallet/sign_digest
             */
            val transferBody = TransferBody(
                TransferArgs(
                    "memtripblock",
                    "memtripissue",
                    "1.0000 SYS",
                    "here is some coins!")
            )

            val transferBodyDigest = AbiBinaryGenTransferWriter(CompressionType.NONE).squishTransferBody(transferBody).toHex()

            val signDigest = walletApi.signDigest(
                asList(transferBodyDigest, privateKey.publicKey.toString())
            ).blockingGet()

            it("should return a transaction receipt") {
                assertNotNull(signDigest.body())
                assertTrue(signDigest.isSuccessful)
            }
        }
    }
})