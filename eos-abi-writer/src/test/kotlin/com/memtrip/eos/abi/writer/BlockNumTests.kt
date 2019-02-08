package com.memtrip.eos.abi.writer

import com.memtrip.eos.abi.writer.bytewriter.DefaultByteWriter
import com.memtrip.eos.core.hex.DefaultHexWriter
import com.memtrip.eos.core.hex.HexWriter
import org.junit.Assert
import org.junit.Test

class BlockNumTests {

    private val hexWriter: HexWriter = DefaultHexWriter()

    @Test
    fun testBlockNum() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putBlockNum(12818018)

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        Assert.assertEquals(hex, "6296")
    }
}