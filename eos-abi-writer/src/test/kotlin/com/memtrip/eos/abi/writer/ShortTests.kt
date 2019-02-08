package com.memtrip.eos.abi.writer

import com.memtrip.eos.abi.writer.bytewriter.DefaultByteWriter
import com.memtrip.eos.core.hex.DefaultHexWriter
import com.memtrip.eos.core.hex.HexWriter
import org.junit.Assert
import org.junit.Test

class ShortTests {

    private val hexWriter: HexWriter = DefaultHexWriter()

    @Test
    fun `put max short`() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putShort(Short.MAX_VALUE)

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        Assert.assertEquals(hex, "ff7f")
    }

    @Test
    fun `put min short`() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putShort(Short.MIN_VALUE)

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        Assert.assertEquals(hex, "0080")
    }
}