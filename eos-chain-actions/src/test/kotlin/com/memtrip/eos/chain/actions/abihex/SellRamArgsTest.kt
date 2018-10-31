package com.memtrip.eos.chain.actions.abihex

import com.memtrip.eos.abi.writer.compression.CompressionType
import com.memtrip.eos.chain.actions.transaction.AbiBinaryGenTransactionWriter
import com.memtrip.eos.chain.actions.transaction.account.actions.sellram.SellRamArgs
import com.memtrip.eos.chain.actions.transaction.account.actions.sellram.SellRamBody
import junit.framework.TestCase.assertEquals
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
class SellRamArgsTest : Spek({

    given("a transaction writer") {

        val transactionWriter by memoized { AbiBinaryGenTransactionWriter(CompressionType.NONE) }

        on("squish the undelegate bandwidth abi model") {

            val sellRamArgs = SellRamArgs(
                "memtripissue",
                4018
            )
            val sellRamBody = SellRamBody(sellRamArgs)

            val output = transactionWriter.squishSellRamBody(sellRamBody).toHex()

            it("should encode bytes as hex") {
                assertEquals("a034c6aeba9ba592b20f000000000000", output)
            }
        }
    }
})