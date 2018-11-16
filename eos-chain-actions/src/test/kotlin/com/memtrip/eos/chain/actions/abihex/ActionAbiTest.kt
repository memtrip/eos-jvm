package com.memtrip.eos.chain.actions.abihex

import com.memtrip.eos.abi.writer.compression.CompressionType
import com.memtrip.eos.chain.actions.transaction.AbiBinaryGenTransactionWriter
import com.memtrip.eos.chain.actions.transaction.abi.ActionAbi
import com.memtrip.eos.chain.actions.transaction.abi.TransactionAuthorizationAbi
import com.memtrip.eos.chain.actions.transaction.account.actions.buyram.BuyRamBytesArgs
import com.memtrip.eos.chain.actions.transaction.account.actions.buyram.BuyRamBytesBody
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.util.Arrays.asList

@RunWith(JUnitPlatform::class)
class ActionAbiTest : Spek({

    given("a transaction writer") {

        on("squish the action abi model") {

            val buyRamBytesAbi = AbiBinaryGenTransactionWriter(CompressionType.NONE).squishBuyRamBytesBody(BuyRamBytesBody(BuyRamBytesArgs(
                "memtripissue",
                "memtripproxy",
                4096
            ))).toHex()

            val actionAbi = ActionAbi(
                "eosio",
                "buyrambytes",
                asList(TransactionAuthorizationAbi(
                    "memtripissue",
                    "active")
                ),
                buyRamBytesAbi)

            val output = AbiBinaryGenTransactionWriter(CompressionType.NONE).squishActionAbi(actionAbi).toHex()

            it("should encode bytes as hex") {
                assertEquals("a034c6aeba9ba592e03bbdb5ba9ba5920010000000000000", buyRamBytesAbi)
                assertEquals("0000000000ea305500b0cafe4873bd3e01a034c6aeba9ba59200000000a8ed323218a034c6aeba9ba592e03bbdb5ba9ba5920010000000000000", output)
            }
        }
    }
})