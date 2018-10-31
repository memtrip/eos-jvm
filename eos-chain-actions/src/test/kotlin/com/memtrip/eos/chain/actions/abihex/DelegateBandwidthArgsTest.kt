package com.memtrip.eos.chain.actions.abihex

import com.memtrip.eos.abi.writer.compression.CompressionType
import com.memtrip.eos.chain.actions.transaction.AbiBinaryGenTransactionWriter
import com.memtrip.eos.chain.actions.transaction.account.actions.delegatebw.DelegateBandwidthArgs
import com.memtrip.eos.chain.actions.transaction.account.actions.delegatebw.DelegateBandwidthBody
import junit.framework.TestCase.assertEquals

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
class DelegateBandwidthArgsTest : Spek({

    given("a transaction writer") {

        val transactionWriter by memoized { AbiBinaryGenTransactionWriter(CompressionType.NONE) }

        on("squish the delegate bandwidth abi model") {

            val delegateBandwidthArgs = DelegateBandwidthArgs(
                "memtripissue",
                "memtripproxy",
                "51.2345 EOS",
                "171.2345 EOS",
                1
            )
            val delegateBandwidthBody = DelegateBandwidthBody(delegateBandwidthArgs)

            val output = transactionWriter.squishDelegateBandwidthBody(delegateBandwidthBody).toHex()

            it("should encode bytes as hex") {
                assertEquals("a034c6aeba9ba592e03bbdb5ba9ba59259d107000000000004454f5300000000d9201a000000000004454f530000000001000000", output)
            }
        }
    }
})