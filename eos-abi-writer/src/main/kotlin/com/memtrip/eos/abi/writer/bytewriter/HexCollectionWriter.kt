package com.memtrip.eos.abi.writer.bytewriter

import com.memtrip.eos.core.hex.DefaultHexWriter
import com.memtrip.eos.core.hex.HexWriter
import com.memtrip.eos.abi.writer.ByteWriter
import org.bitcoinj.core.Sha256Hash

class HexCollectionWriter(
    private val hexWriter: HexWriter = DefaultHexWriter()
) {

    fun put(hexList: List<String>, writer: ByteWriter) {
        writer.putBytes(hexCollectionBytes(hexList))
    }

    private fun hexCollectionBytes(hexList: List<String>): ByteArray {
        if (hexList.isEmpty()) {
            return ZERO_HASH
        }

        val writer = DefaultByteWriter(255)

        writer.putVariableUInt(hexList.size.toLong())

        for (string in hexList) {
            val stringBytes = hexWriter.hexToBytes(string)
            writer.putVariableUInt(stringBytes.size.toLong())
            writer.putBytes(stringBytes)
        }

        return Sha256Hash.hash(writer.toBytes())
    }

    companion object {
        private const val HASH_LENGTH = 32
        private val ZERO_HASH = ByteArray(HASH_LENGTH)
    }
}