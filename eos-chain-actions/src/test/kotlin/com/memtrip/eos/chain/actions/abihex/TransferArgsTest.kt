package com.memtrip.eos.chain.actions.abihex

import com.memtrip.eos.abi.writer.compression.CompressionType
import com.memtrip.eos.chain.actions.transaction.AbiBinaryGenTransactionWriter

import com.memtrip.eos.chain.actions.transaction.transfer.actions.TransferArgs
import com.memtrip.eos.chain.actions.transaction.transfer.actions.TransferBody
import junit.framework.Assert.assertEquals
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
class TransferArgsTest : Spek({

    given("a transaction writer") {

        val transactionWriter by memoized { AbiBinaryGenTransactionWriter(CompressionType.NONE) }

        on("squish the transfer abi model") {

            val transferArgs = TransferArgs(
                "memtripissu5",
                "memtripblock",
                "12.3040 EOS",
                "this is a memo"
            )
            val transferBody = TransferBody(transferArgs)

            val output = transactionWriter.squishTransferBody(transferBody).toHex()

            it("should encode bytes as hex") {
                assertEquals("5034c6aeba9ba59200118da7ba9ba592a0e001000000000004454f53000000000e746869732069732061206d656d6f", output)
            }
        }

        on("transfer abi model with Cyrillic letters") {

            val transferArgs = TransferArgs(
                "memtripissu5",
                "memtripblock",
                "12.3040 EOS",
                "тест"
            )
            val transferBody = TransferBody(transferArgs)

            val output = transactionWriter.squishTransferBody(transferBody).toHex()

            it("should encode letters correctly as hex") {
                assertEquals("5034c6aeba9ba59200118da7ba9ba592a0e001000000000004454f530000000008d182d0b5d181d182", output)
            }
        }

        on("transfer abi model with Cyrillic letters 2") {

            val transferArgs = TransferArgs(
                "memtripissu5",
                "memtripblock",
                "12.3040 EOS",
                "тестируем testing 1234567890"
            )
            val transferBody = TransferBody(transferArgs)

            val output = transactionWriter.squishTransferBody(transferBody).toHex()

            it("should encode letters correctly as hex") {
                assertEquals("5034c6aeba9ba59200118da7ba9ba592a0e001000000000004454f530000000025d182d0b5d181d182d0b8d180d183d0b5d0bc2074657374696e672031323334353637383930", output)
            }
        }
    }
})