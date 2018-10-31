package com.memtrip.eos.chain.actions.abihex

import com.memtrip.eos.abi.writer.compression.CompressionType
import com.memtrip.eos.chain.actions.transaction.AbiBinaryGenTransactionWriter
import com.memtrip.eos.chain.actions.transaction.account.actions.undelegatebw.UnDelegateBandwidthArgs
import com.memtrip.eos.chain.actions.transaction.account.actions.undelegatebw.UnDelegateBandwidthBody
import junit.framework.TestCase.assertEquals
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
class UnDelegateBandwidthArgsTest : Spek({

    given("a transaction writer") {

        val transactionWriter by memoized { AbiBinaryGenTransactionWriter(CompressionType.NONE) }

        on("squish the undelegate bandwidth abi model") {

            val undelegateBandwidthArgs = UnDelegateBandwidthArgs(
                "memtripissue",
                "memtripproxy",
                "51.2345 EOS",
                "171.2345 EOS"
            )
            val unDelegateBandwidthBody = UnDelegateBandwidthBody(undelegateBandwidthArgs)

            val output = transactionWriter.squishUnDelegateBandwidthBody(unDelegateBandwidthBody).toHex()

            it("should encode bytes as hex") {
                assertEquals("a034c6aeba9ba592e03bbdb5ba9ba59259d107000000000004454f5300000000d9201a000000000004454f5300000000", output)
            }
        }
    }
})