package com.memtrip.eos.chain.actions.abihex

import com.memtrip.eos.abi.writer.compression.CompressionType
import com.memtrip.eos.chain.actions.transaction.AbiBinaryGenTransactionWriter
import com.memtrip.eos.chain.actions.transaction.account.actions.newaccount.AccountKeyAbi
import com.memtrip.eos.chain.actions.transaction.account.actions.newaccount.AccountRequiredAuthAbi
import com.memtrip.eos.chain.actions.transaction.account.actions.newaccount.NewAccountArgs
import com.memtrip.eos.chain.actions.transaction.account.actions.newaccount.NewAccountBody
import junit.framework.TestCase.assertEquals
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.util.asList

@RunWith(JUnitPlatform::class)
class NewAccountArgsTest : Spek({

    given("a transaction writer") {

        val transactionWriter by memoized { AbiBinaryGenTransactionWriter(CompressionType.NONE) }

        on("squish the newaccount abi model") {

            val newAccountArgs = NewAccountArgs(
                "memtripissue",
                "memtripblock",
                AccountRequiredAuthAbi(
                    1,
                    asList(
                        AccountKeyAbi(
                            "EOS7iZvLHdreeArYBGGat3Ciky5eR8mwBt1TD8pekcGEZCe1gVVQH",
                            1)
                    ),
                    emptyList(),
                    emptyList()
                ),
                AccountRequiredAuthAbi(
                    1,
                    asList(
                        AccountKeyAbi(
                            "EOS7iZvLHdreeArYBGGat3Ciky5eR8mwBt1TD8pekcGEZCe1gVVQH",
                            1)
                    ),
                    emptyList(),
                    emptyList()
                )
            )
            val newAccountBody = NewAccountBody(newAccountArgs)

            val output = transactionWriter.squishNewAccountBody(newAccountBody).toHex()

            it("should encode bytes as hex") {
                assertEquals("a034c6aeba9ba59200118da7ba9ba59201000000010003748f9366f5a1c2a1e04811d300b520a0715a9a9aa0ecb88bafd93923626a5e390100000001000000010003748f9366f5a1c2a1e04811d300b520a0715a9a9aa0ecb88bafd93923626a5e3901000000", output)
            }
        }
    }
})