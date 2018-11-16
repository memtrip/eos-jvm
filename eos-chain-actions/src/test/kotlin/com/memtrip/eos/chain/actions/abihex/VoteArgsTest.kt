package com.memtrip.eos.chain.actions.abihex

import com.memtrip.eos.abi.writer.compression.CompressionType
import com.memtrip.eos.chain.actions.transaction.AbiBinaryGenTransactionWriter

import com.memtrip.eos.chain.actions.transaction.vote.actions.VoteArgs
import com.memtrip.eos.chain.actions.transaction.vote.actions.VoteBody
import junit.framework.Assert.assertEquals
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.util.Arrays.asList

@RunWith(JUnitPlatform::class)
class VoteArgsTest : Spek({

    given("a transaction writer") {

        val transactionWriter by memoized { AbiBinaryGenTransactionWriter(CompressionType.NONE) }

        on("squish the proxy vote abi model") {

            val voteArgs = VoteArgs(
                "memtripissue",
                "memtripproxy",
                emptyList()
            )
            val voteBody = VoteBody(voteArgs)

            val output = transactionWriter.squishVoteBody(voteBody).toHex()

            it("should encode bytes as hex") {
                assertEquals("a034c6aeba9ba592e03bbdb5ba9ba59200", output)
            }
        }

        on("squish the producer vote abi model") {

            val voteArgs = VoteArgs(
                "memtripissue",
                "",
                asList("memtripblock")
            )
            val voteBody = VoteBody(voteArgs)

            val output = transactionWriter.squishVoteBody(voteBody).toHex()

            it("should encode bytes as hex") {
                assertEquals("a034c6aeba9ba59200000000000000000100118da7ba9ba592", output)
            }
        }
    }
})