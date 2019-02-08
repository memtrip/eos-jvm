package com.memtrip.eos.abi.writer

import com.memtrip.eos.abi.writer.bytewriter.DefaultByteWriter
import com.memtrip.eos.core.hex.DefaultHexWriter
import com.memtrip.eos.core.hex.HexWriter
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

class DataTests {

    private val hexWriter: HexWriter = DefaultHexWriter()

    @Test
    fun `put Buy ram abi data`() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putData("a034c6aeba9ba592e03bbdb5ba9ba59259d107000000000004454f5300000000")

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        Assert.assertEquals(hex, "20a034c6aeba9ba592e03bbdb5ba9ba59259d107000000000004454f5300000000")
    }

    @Test
    fun `put Buy ram bytes abi data`() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putData("a034c6aeba9ba592e03bbdb5ba9ba5920010000000000000")

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        Assert.assertEquals(hex, "18a034c6aeba9ba592e03bbdb5ba9ba5920010000000000000")
    }

    @Test
    fun `put delegate bandwidth abi data`() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putData("a034c6aeba9ba592e03bbdb5ba9ba59259d107000000000004454f5300000000d9201a000000000004454f530000000001000000")

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        assertEquals(hex, "34a034c6aeba9ba592e03bbdb5ba9ba59259d107000000000004454f5300000000d9201a000000000004454f530000000001000000")
    }

    @Test
    fun `put new account abi data`() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putData("a034c6aeba9ba59200118da7ba9ba59201000000010003748f9366f5a1c2a1e04811d300b520a0715a9a9aa0ecb88bafd93923626a5e390100000001000000010003748f9366f5a1c2a1e04811d300b520a0715a9a9aa0ecb88bafd93923626a5e3901000000")

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        assertEquals(hex, "66a034c6aeba9ba59200118da7ba9ba59201000000010003748f9366f5a1c2a1e04811d300b520a0715a9a9aa0ecb88bafd93923626a5e390100000001000000010003748f9366f5a1c2a1e04811d300b520a0715a9a9aa0ecb88bafd93923626a5e3901000000")
    }

    @Test
    fun `put refund abi data`() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putData("a034c6aeba9ba592")

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        assertEquals(hex, "08a034c6aeba9ba592")
    }

    @Test
    fun `put sell ram abi data`() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putData("a034c6aeba9ba592b20f000000000000")

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        assertEquals(hex, "10a034c6aeba9ba592b20f000000000000")
    }

    @Test
    fun `put transfer abi data`() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putData("5034c6aeba9ba59200118da7ba9ba592a0e001000000000004454f530000000025d182d0b5d181d182d0b8d180d183d0b5d0bc2074657374696e672031323334353637383930")

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        assertEquals(hex, "465034c6aeba9ba59200118da7ba9ba592a0e001000000000004454f530000000025d182d0b5d181d182d0b8d180d183d0b5d0bc2074657374696e672031323334353637383930")
    }

    @Test
    fun `put undelegated bw abi data`() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putData("a034c6aeba9ba592e03bbdb5ba9ba59259d107000000000004454f5300000000d9201a000000000004454f5300000000")

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        assertEquals(hex, "30a034c6aeba9ba592e03bbdb5ba9ba59259d107000000000004454f5300000000d9201a000000000004454f5300000000")
    }

    @Test
    fun `put proxy vote abi data`() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putData("a034c6aeba9ba592e03bbdb5ba9ba59200")

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        assertEquals(hex, "11a034c6aeba9ba592e03bbdb5ba9ba59200")
    }

    @Test
    fun `put producer vote abi data`() {

        // given
        val byteWriter = DefaultByteWriter(500)

        // when
        byteWriter.putData("a034c6aeba9ba59200000000000000000100118da7ba9ba592")

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        assertEquals(hex, "19a034c6aeba9ba59200000000000000000100118da7ba9ba592")
    }
}