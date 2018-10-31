package com.memtrip.eos.chain.actions.abihex

import com.memtrip.eos.abi.writer.compression.CompressionType
import com.memtrip.eos.chain.actions.transaction.AbiBinaryGenTransactionWriter
import com.memtrip.eos.chain.actions.transaction.account.actions.buyram.BuyRamBytesArgs
import com.memtrip.eos.chain.actions.transaction.account.actions.buyram.BuyRamBytesBody
import junit.framework.TestCase.assertEquals

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
class BuyRamBytesArgsTest : Spek({

    given("a transaction writer") {

        val transactionWriter by memoized { AbiBinaryGenTransactionWriter(CompressionType.NONE) }

        on("squish the buy ram bytes abi model") {

            val buyRamBytesArgs = BuyRamBytesArgs(
                "memtripissue",
                "memtripproxy",
                4096
            )
            val buyRamBytesBody = BuyRamBytesBody(buyRamBytesArgs)

            val output = transactionWriter.squishBuyRamBytesBody(buyRamBytesBody).toHex()

            it("should encode bytes as hex") {
                assertEquals("a034c6aeba9ba592e03bbdb5ba9ba5920010000000000000", output)
            }
        }
    }
})