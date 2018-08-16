package com.memtrip.eos.core.hash

import com.memtrip.eos.core.hex.DefaultHexWriter

import org.junit.Assert.assertTrue
import org.junit.Test

class HmacTests {

    @Test
    fun testVector1() {

        val hexWriter = DefaultHexWriter()

        val key = hexWriter.hexToBytes("0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b")
        val data = hexWriter.hexToBytes("4869205468657265")
        val expected_256 = hexWriter.hexToBytes("b0344c61d8db38535ca8afceaf0bf12b881dc200c9833da726e9376c2e32cff7")
        val result_256 = HMac.hash(key, data)

        assertTrue(result_256.contentEquals(expected_256))
    }

    @Test
    fun testVector2() {

        val hexWriter = DefaultHexWriter()

        val key = hexWriter.hexToBytes("4a656665")
        val data = hexWriter.hexToBytes("7768617420646f2079612077616e7420666f72206e6f7468696e673f")
        val expected_256 = hexWriter.hexToBytes("5bdcc146bf60754e6a042426089575c75a003f089d2739839dec58b964ec3843")
        val result_256 = HMac.hash(key, data)

        assertTrue(result_256.contentEquals(expected_256))
    }

    @Test
    fun testVector3() {

        val hexWriter = DefaultHexWriter()

        val key = hexWriter.hexToBytes("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
        val data = hexWriter.hexToBytes("dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd")
        val expected_256 = hexWriter.hexToBytes("773ea91e36800e46854db8ebd09181a72959098b3ef8c122d9635514ced565fe")
        val result_256 = HMac.hash(key, data)

        assertTrue(result_256.contentEquals(expected_256))
    }
}