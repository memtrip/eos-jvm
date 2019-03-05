package com.memtrip.eos.abi.writer

import com.memtrip.eos.abi.writer.bytewriter.DefaultByteWriter
import com.memtrip.eos.core.hex.DefaultHexWriter
import com.memtrip.eos.core.hex.HexWriter
import org.junit.Assert.assertEquals
import org.junit.Test

class FloatTests {

    private val hexWriter: HexWriter = DefaultHexWriter()

    @Test
    fun `put max float value`() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putFloat(Float.MAX_VALUE)

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        assertEquals(hex, "ffff7f7f")
    }

    @Test
    fun `put float value`() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putFloat(kotlin.math.PI.toFloat())

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        assertEquals(hex, "db0f4940")
    }

    @Test
    fun `put min float value`() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putFloat(Float.MIN_VALUE)

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        assertEquals(hex, "01000000")
    }
}