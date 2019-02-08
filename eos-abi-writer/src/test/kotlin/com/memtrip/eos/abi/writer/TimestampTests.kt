package com.memtrip.eos.abi.writer

import com.memtrip.eos.abi.writer.bytewriter.DefaultByteWriter
import com.memtrip.eos.core.hex.DefaultHexWriter
import com.memtrip.eos.core.hex.HexWriter
import org.junit.Assert.assertEquals
import org.junit.Test

class TimestampTests {

    private val hexWriter: HexWriter = DefaultHexWriter()

    @Test
    fun testTimestamp() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putTimestampMs(1549648892253)

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        assertEquals(hex, "fcc35d5c")
    }
}