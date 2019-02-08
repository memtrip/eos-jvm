package com.memtrip.eos.abi.writer

import com.memtrip.eos.abi.writer.bytewriter.DefaultByteWriter
import com.memtrip.eos.core.hex.DefaultHexWriter
import com.memtrip.eos.core.hex.HexWriter
import org.junit.Assert.assertEquals
import org.junit.Test

class LongTests {

    private val hexWriter: HexWriter = DefaultHexWriter()

    @Test
    fun `put max long value`() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putLong(Long.MAX_VALUE)

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        assertEquals(hex, "ffffffffffffff7f")
    }

    @Test
    fun `put long value`() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putLong(1549654354968L)

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        assertEquals(hex, "18ec98ce68010000")
    }

    @Test
    fun `put min long value`() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putLong(Long.MIN_VALUE)

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        assertEquals(hex, "0000000000000080")
    }
}