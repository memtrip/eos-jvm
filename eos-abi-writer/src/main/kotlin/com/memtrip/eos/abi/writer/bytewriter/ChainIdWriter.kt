package com.memtrip.eos.abi.writer.bytewriter

import com.memtrip.eos.abi.writer.ByteWriter

class ChainIdWriter {

    fun put(chainId: String, writer: ByteWriter) {
        writer.putBytes(getSha256FromHexStr(chainId))
    }

    private fun getSha256FromHexStr(str: String): ByteArray {
        val len = str.length
        val bytes = ByteArray(32)
        var i = 0
        while (i < len) {
            val strIte = str.substring(i, i + 2)
            val n = Integer.parseInt(strIte, 16) and 0xFF
            bytes[i / 2] = n.toByte()
            i += 2
        }
        return bytes
    }
}