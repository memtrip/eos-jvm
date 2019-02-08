package com.memtrip.eos.abi.writer

import com.memtrip.eos.abi.writer.bytewriter.DefaultByteWriter
import com.memtrip.eos.core.crypto.EosPublicKey
import com.memtrip.eos.core.hex.DefaultHexWriter
import com.memtrip.eos.core.hex.HexWriter
import org.junit.Assert.assertEquals
import org.junit.Test

class PublicKeyWriterTests {

    private val hexWriter: HexWriter = DefaultHexWriter()

    @Test
    fun testPublicKey() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putPublicKey(EosPublicKey("EOS6MRyAjQq8ud7hVNYcfnVPJqcVpscN5So8BhtHuGYqET5GDW5CV"))

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        assertEquals(hex, "0002c0ded2bc1f1305fb0faac5e6c03ee3a1924234985427b6167ca569d13df435cf")
    }
}