package com.memtrip.eos.core.block

import com.memtrip.eos.core.hex.DefaultHexWriter
import com.memtrip.eos.core.hex.HexWriter

import org.bitcoinj.core.Utils
import java.math.BigInteger

data class BlockIdDetails(
    val blockId: String,
    val hexWriter: HexWriter = DefaultHexWriter(),
    val blockNum: Int = BigInteger( 1, hexWriter.hexToBytes(blockId.substring(0, 8))).toInt(),
    val blockPrefix: Long = Utils.readUint32(hexWriter.hexToBytes(blockId.substring(16, 24)), 0)
)