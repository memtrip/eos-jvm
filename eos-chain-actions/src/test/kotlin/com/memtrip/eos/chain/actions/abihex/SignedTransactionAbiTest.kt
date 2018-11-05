package com.memtrip.eos.chain.actions.abihex

import com.memtrip.eos.abi.writer.compression.CompressionType
import com.memtrip.eos.chain.actions.transaction.AbiBinaryGenTransactionWriter
import com.memtrip.eos.chain.actions.transaction.abi.ActionAbi
import com.memtrip.eos.chain.actions.transaction.abi.SignedTransactionAbi
import com.memtrip.eos.chain.actions.transaction.abi.TransactionAbi
import com.memtrip.eos.chain.actions.transaction.abi.TransactionAuthorizationAbi
import com.memtrip.eos.chain.actions.transaction.account.actions.buyram.BuyRamBytesArgs
import com.memtrip.eos.chain.actions.transaction.account.actions.buyram.BuyRamBytesBody
import com.memtrip.eos.chain.actions.transaction.account.actions.delegatebw.DelegateBandwidthArgs
import com.memtrip.eos.chain.actions.transaction.account.actions.delegatebw.DelegateBandwidthBody
import com.memtrip.eos.chain.actions.transaction.account.actions.newaccount.AccountKeyAbi
import com.memtrip.eos.chain.actions.transaction.account.actions.newaccount.AccountRequiredAuthAbi
import com.memtrip.eos.chain.actions.transaction.account.actions.newaccount.NewAccountArgs
import com.memtrip.eos.chain.actions.transaction.account.actions.newaccount.NewAccountBody

import com.memtrip.eos.core.block.BlockIdDetails
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.util.Arrays.asList
import java.util.Date

@RunWith(JUnitPlatform::class)
class SignedTransactionAbiTest : Spek({

    given("a transaction writer") {

        on("squish the transaction abi model") {

            val chainId = "cf057bbfb72640471fd910bcb67639c22df9f92470936cddc1ade0e2f2e7dc4f"
            val blockId = "00008009f6a3e931273c63d1caa4bb2d1520223251cf3ea939ffe29a770471f7"
            val blockIdDetails = BlockIdDetails(blockId)

            val accountName = "memtripissue"
            val expirationDate = Date(1541086024002)

            val newAccountAbiHex = AbiBinaryGenTransactionWriter(CompressionType.NONE).squishNewAccountBody(NewAccountBody(NewAccountArgs(
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
            ))).toHex()

            val buyRamBytesAbiHex = AbiBinaryGenTransactionWriter(CompressionType.NONE).squishBuyRamBytesBody(BuyRamBytesBody(BuyRamBytesArgs(
                "memtripissue",
                "memtripproxy",
                4096
            ))).toHex()

            val delegateBandwidthAbiHex = AbiBinaryGenTransactionWriter(CompressionType.NONE).squishDelegateBandwidthBody(DelegateBandwidthBody(DelegateBandwidthArgs(
                "memtripissue",
                "memtripproxy",
                "51.2345 EOS",
                "171.2345 EOS",
                1
            ))).toHex()

            val transactionAbi = TransactionAbi(
                expirationDate,
                blockIdDetails.blockNum,
                blockIdDetails.blockPrefix,
                0,
                0,
                0,
                emptyList(),
                asList(
                    ActionAbi(
                        "eosio",
                        "newaccount",
                        asList(TransactionAuthorizationAbi(
                            accountName,
                            "active")
                        ),
                        newAccountAbiHex
                    ),
                    ActionAbi(
                        "eosio",
                        "buyrambytes",
                        asList(TransactionAuthorizationAbi(
                            accountName,
                            "active")
                        ),
                        buyRamBytesAbiHex
                    ),
                    ActionAbi(
                        "eosio",
                        "delegatebw",
                        asList(TransactionAuthorizationAbi(
                            accountName,
                            "active")
                        ),
                        delegateBandwidthAbiHex
                    )
                ),
                emptyList(),
                emptyList(),
                emptyList())

            val transactionAbiHex = AbiBinaryGenTransactionWriter(CompressionType.NONE).squishTransactionAbi(transactionAbi).toHex()

            val signedTransactionAbi = SignedTransactionAbi(
                chainId,
                transactionAbi,
                emptyList())

            val signedTransactionHex = AbiBinaryGenTransactionWriter(CompressionType.NONE).squishSignedTransactionAbi(signedTransactionAbi).toHex()

            it("should encode bytes as hex") {
                assertEquals("a034c6aeba9ba59200118da7ba9ba59201000000010003748f9366f5a1c2a1e04811d300b520a0715a9a9aa0ecb88bafd93923626a5e390100000001000000010003748f9366f5a1c2a1e04811d300b520a0715a9a9aa0ecb88bafd93923626a5e3901000000", newAccountAbiHex)
                assertEquals("a034c6aeba9ba592e03bbdb5ba9ba5920010000000000000", buyRamBytesAbiHex)
                assertEquals("a034c6aeba9ba592e03bbdb5ba9ba59259d107000000000004454f5300000000d9201a000000000004454f530000000001000000", delegateBandwidthAbiHex)
                assertEquals("481bdb5b0980273c63d100000000030000000000ea305500409e9a2264b89a01a034c6aeba9ba59200000000a8ed323266a034c6aeba9ba59200118da7ba9ba59201000000010003748f9366f5a1c2a1e04811d300b520a0715a9a9aa0ecb88bafd93923626a5e390100000001000000010003748f9366f5a1c2a1e04811d300b520a0715a9a9aa0ecb88bafd93923626a5e39010000000000000000ea305500b0cafe4873bd3e01a034c6aeba9ba59200000000a8ed323218a034c6aeba9ba592e03bbdb5ba9ba59200100000000000000000000000ea305500003f2a1ba6a24a01a034c6aeba9ba59200000000a8ed323234a034c6aeba9ba592e03bbdb5ba9ba59259d107000000000004454f5300000000d9201a000000000004454f53000000000100000000", transactionAbiHex)
                assertEquals("cf057bbfb72640471fd910bcb67639c22df9f92470936cddc1ade0e2f2e7dc4f481bdb5b0980273c63d100000000030000000000ea305500409e9a2264b89a01a034c6aeba9ba59200000000a8ed323266a034c6aeba9ba59200118da7ba9ba59201000000010003748f9366f5a1c2a1e04811d300b520a0715a9a9aa0ecb88bafd93923626a5e390100000001000000010003748f9366f5a1c2a1e04811d300b520a0715a9a9aa0ecb88bafd93923626a5e39010000000000000000ea305500b0cafe4873bd3e01a034c6aeba9ba59200000000a8ed323218a034c6aeba9ba592e03bbdb5ba9ba59200100000000000000000000000ea305500003f2a1ba6a24a01a034c6aeba9ba59200000000a8ed323234a034c6aeba9ba592e03bbdb5ba9ba59259d107000000000004454f5300000000d9201a000000000004454f530000000001000000000000000000000000000000000000000000000000000000000000000000000000", signedTransactionHex)
            }
        }
    }
})