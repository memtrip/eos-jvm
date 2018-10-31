package com.memtrip.eos.chain.actions.abihex

import com.memtrip.eos.abi.writer.compression.CompressionType
import com.memtrip.eos.chain.actions.transaction.AbiBinaryGenTransactionWriter
import com.memtrip.eos.chain.actions.transaction.account.actions.buyram.BuyRamArgs
import com.memtrip.eos.chain.actions.transaction.account.actions.buyram.BuyRamBody
import junit.framework.TestCase.assertEquals

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
class BuyRamArgsTest : Spek({

    given("a transaction writer") {

        val transactionWriter by memoized { AbiBinaryGenTransactionWriter(CompressionType.NONE) }

        on("squish the buy ram abi model") {

            val buyRamArgs = BuyRamArgs(
                "memtripissue",
                "memtripproxy",
                "51.2345 EOS"
            )
            val buyRamBody = BuyRamBody(buyRamArgs)

            val output = transactionWriter.squishBuyRamBody(buyRamBody).toHex()

            it("should encode bytes as hex") {
                assertEquals("a034c6aeba9ba592e03bbdb5ba9ba59259d107000000000004454f5300000000", output)
            }
        }
    }
})