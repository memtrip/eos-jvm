package com.memtrip.eos.abi.writer

import com.memtrip.eos.abi.writer.bytewriter.DefaultByteWriter
import com.memtrip.eos.core.hex.DefaultHexWriter
import com.memtrip.eos.core.hex.HexWriter

import org.junit.Assert.assertEquals
import org.junit.Test

class AccountNameTests {

    private val hexWriter: HexWriter = DefaultHexWriter()

    @Test
    fun testAccountName() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putAccountName("memtripissue")

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        assertEquals(hex, "a034c6aeba9ba592")
    }

    @Test
    fun testName() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putName("memtripblock")

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        assertEquals(hex, "00118da7ba9ba592")
    }

    @Test
    fun testAccountNameCollection() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putAccountNameCollection(listOf(
            "memtripissue",
            "memtripblock"))

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        assertEquals(hex, "02a034c6aeba9ba59200118da7ba9ba592")
    }
}