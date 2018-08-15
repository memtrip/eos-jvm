package com.memtrip.eosio.abi.binary

interface HexWriter {

    fun bytesToHex(bytes: ByteArray, offset: Int, length: Int, separator: String?): String

    fun hexToBytes(hex: String): ByteArray
}