package com.memtrip.eos.chain.actions.abihex

import com.memtrip.eos.abi.writer.compression.CompressionType
import com.memtrip.eos.chain.actions.transaction.AbiBinaryGenTransactionWriter
import com.memtrip.eos.chain.actions.transaction.account.actions.refund.RefundArgs
import com.memtrip.eos.chain.actions.transaction.account.actions.refund.RefundBody
import junit.framework.TestCase
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
class RefundArgsTest : Spek({

    given("a transaction writer") {

        val transactionWriter by memoized { AbiBinaryGenTransactionWriter(CompressionType.NONE) }

        on("squish the refund abi model") {

            val refundArgs = RefundArgs("memtripissue")
            val refundBody = RefundBody(refundArgs)

            val output = transactionWriter.squishRefundBody(refundBody).toHex()

            it("should encode bytes as hex") {
                TestCase.assertEquals("a034c6aeba9ba592", output)
            }
        }
    }
})