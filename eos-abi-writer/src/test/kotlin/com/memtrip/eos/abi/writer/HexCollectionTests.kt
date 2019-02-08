package com.memtrip.eos.abi.writer

import com.memtrip.eos.abi.writer.bytewriter.DefaultByteWriter
import com.memtrip.eos.core.hex.DefaultHexWriter
import com.memtrip.eos.core.hex.HexWriter
import org.junit.Assert.assertEquals
import org.junit.Test

class HexCollectionTests {

    private val hexWriter: HexWriter = DefaultHexWriter()

    @Test
    fun testHexCollection() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putHexCollection(listOf(
            "aca376f206b8fc25a6ed44dbdc66547c36c6c33e3a119ffbeaef943642f0e906",
            "027a395bebe608bda318c38e9ff005fa48a77c83f39c07c55734da416e67e609"))

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        assertEquals(hex, "e9d8fece02c013d7299ba233124832858d96c599b490add7d672bd05b42b7571")
    }
}