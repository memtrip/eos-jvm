package com.memtrip.eos.abi.writer

import com.memtrip.eos.abi.writer.bytewriter.DefaultByteWriter
import com.memtrip.eos.core.hex.DefaultHexWriter
import com.memtrip.eos.core.hex.HexWriter

import org.junit.Assert.assertEquals
import org.junit.Test

class ChainIdTests {

    private val hexWriter: HexWriter = DefaultHexWriter()

    @Test
    fun testChainId() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putChainId("aca376f206b8fc25a6ed44dbdc66547c36c6c33e3a119ffbeaef943642f0e906")

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        assertEquals(hex, "aca376f206b8fc25a6ed44dbdc66547c36c6c33e3a119ffbeaef943642f0e906")
    }
}