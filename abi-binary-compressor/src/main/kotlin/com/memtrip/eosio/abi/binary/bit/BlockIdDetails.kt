package com.memtrip.eosio.abi.binary.bit

import com.memtrip.eosio.abi.binary.HexWriter
import com.memtrip.eosio.abi.binary.writer.DefaultHexWriter
import java.math.BigInteger

data class BlockIdDetails(
    val blockId: String,
    val hexWriter: HexWriter = DefaultHexWriter(),
    val blockNum: Int = BigInteger( 1, hexWriter.hexToBytes(blockId.substring(0,8))).toInt(),
    val blockPrefix: Long = BitUtils.uint32ToLong(hexWriter.hexToBytes(blockId.substring(16,24)), 0))