package com.memtrip.eos.abi.writer

import com.memtrip.eos.abi.writer.bytewriter.DefaultByteWriter
import com.memtrip.eos.core.hex.DefaultHexWriter
import com.memtrip.eos.core.hex.HexWriter
import org.junit.Assert
import org.junit.Test

class AssetTests {

    private val hexWriter: HexWriter = DefaultHexWriter()

    @Test
    fun `0_0001 EOS`() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putAsset("0.0001 EOS")

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        Assert.assertEquals(hex, "010000000000000004454f5300000000")
    }

    @Test
    fun `1000_0000 EOS`() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putAsset("1000.0000 EOS")

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        Assert.assertEquals(hex, "809698000000000004454f5300000000")
    }

    @Test
    fun `1000000_0000 EOS`() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putAsset("1000000.0000 EOS")

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        Assert.assertEquals(hex, "00e40b540200000004454f5300000000")
    }

    @Test
    fun `10000000000_0000 EOS`() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putAsset("10000000000.0000 EOS")

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        Assert.assertEquals(hex, "00407a10f35a000004454f5300000000")
    }

    @Test
    fun `9876_5432 EOS`() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putAsset("9876.5432 EOS")

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        Assert.assertEquals(hex, "780ae3050000000004454f5300000000")
    }

    @Test
    fun `0_1234 EOS`() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putAsset("0.1234 EOS")

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        Assert.assertEquals(hex, "d20400000000000004454f5300000000")
    }

    @Test
    fun `0_123 WALR`() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putAsset("0.123 WALR")

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        Assert.assertEquals(hex, "7b000000000000000357414c52000000")
    }

    @Test
    fun `9999_57 CHOMP`() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putAsset("9999.57 CHOMP")

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        Assert.assertEquals(hex, "15420f00000000000243484f4d500000")
    }
}