package com.memtrip.eos.http.rpc.chain

import com.memtrip.eos.http.rpc.Api
import com.memtrip.eos.http.rpc.Config
import com.memtrip.eos.http.rpc.model.block.request.BlockNumOrId
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
class GetBlockHeaderStateTest : Spek({

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

        on("v1/chain/get_block_header_state") {

            val info = chainApi.getInfo().blockingGet()

            val block = chainApi.getBlockHeaderState(BlockNumOrId(info.body()!!.last_irreversible_block_num.toString())).blockingGet()

            it("should return block header state") {
                assertTrue(block.isSuccessful)
                assertNotNull(block.body())
            }
        }
    }
})