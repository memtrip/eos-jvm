package com.memtrip.eos.abi.writer

import com.memtrip.eos.abi.writer.bytewriter.DefaultByteWriter
import com.memtrip.eos.core.hex.DefaultHexWriter
import com.memtrip.eos.core.hex.HexWriter
import org.junit.Assert.assertEquals

import org.junit.Test

class StringWriterTests {

    private val hexWriter: HexWriter = DefaultHexWriter()

    @Test
    fun testAsciiString() {

        // given
        val byteWriter = DefaultByteWriter(512)

        // when
        byteWriter.putString("Hello there, this is a basic ascii string.")

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        assertEquals(hex, "2a48656c6c6f2074686572652c2074686973206973206120626173696320617363696920737472696e672e")
    }

    @Test
    fun testCyrillicLetters() {

        // given
        val byteWriter = DefaultByteWriter(512)

        // when
        byteWriter.putString("тест")

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        assertEquals(hex, "08d182d0b5d181d182")
    }

    @Test
    fun testCyrillicLetters2() {

        // given
        val byteWriter = DefaultByteWriter(512)

        // when
        byteWriter.putString("тестируем testing 1234567890")

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        assertEquals(hex, "25d182d0b5d181d182d0b8d180d183d0b5d0bc2074657374696e672031323334353637383930")
    }

    @Test
    fun testChineseCharacters() {

        // given
        val byteWriter = DefaultByteWriter(512)

        // when
        byteWriter.putString("轮子把巨人挤出局")

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        assertEquals(hex, "18e8bdaee5ad90e68a8ae5b7a8e4babae68ca4e587bae5b180")
    }

    @Test
    fun testUnicode() {
        for (index in 0..65535) {
            with(DefaultByteWriter(500)) {
                putString(index.toChar().toString())
                hexWriter.bytesToHex(toBytes())
            }
        }
    }

    @Test
    fun testStringCollection() {

        // given
        val byteWriter = DefaultByteWriter(512)

        // when
        byteWriter.putStringCollection(listOf("hello", "goodbye"))

        // then
        val hex = hexWriter.bytesToHex(byteWriter.toBytes())
        assertEquals(hex, "020568656c6c6f07676f6f64627965")
    }
}