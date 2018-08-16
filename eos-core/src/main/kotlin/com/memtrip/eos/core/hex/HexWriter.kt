package com.memtrip.eos.core.hex

interface HexWriter {

    fun bytesToHex(bytes: ByteArray): String

    fun bytesToHex(bytes: ByteArray, offset: Int, length: Int, separator: String?): String

    fun hexToBytes(hex: String): ByteArray
}