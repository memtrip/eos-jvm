package com.memtrip.eos.abi.writer

import com.memtrip.eos.abi.writer.bytewriter.DefaultByteWriter
import com.memtrip.eos.core.hex.DefaultHexWriter
import com.memtrip.eos.core.hex.HexWriter
import org.junit.Assert
import org.junit.Test

class BlockPrefixTests {

    private val hexWriter: HexWriter = DefaultHexWriter()

    @Test
    fun testBlockPrefix() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putBlockPrefix(1992007324)

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        Assert.assertEquals(hex, "9c9ebb76")
    }
}