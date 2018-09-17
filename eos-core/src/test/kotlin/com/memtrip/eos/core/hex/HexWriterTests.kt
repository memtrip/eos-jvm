package com.memtrip.eos.core.hex

import org.junit.Assert.assertEquals

import org.junit.Test

class HexWriterTests {

    @Test
    fun bytesToHex() {

        val hexWriter: HexWriter = DefaultHexWriter()

        val testString = "These are some bytes that should be written to a hexadecimal string representation."
        val testAsHex = hexWriter.bytesToHex(testString.toByteArray())
        val testAsBytes = hexWriter.hexToBytes(testAsHex)

        assertEquals(testString, String(testAsBytes))
    }
}