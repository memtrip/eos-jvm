package com.memtrip.eos.core.block

import org.junit.Assert.assertEquals
import org.junit.Test

class BlockIdDetailsTest {

    @Test
    fun blockIdDetails() {
        val blockIdDetails = BlockIdDetails("0000000ac7619ca01df1e0b4964921020e772ceb7343ec51f65537cdbce192d3")
        assertEquals(blockIdDetails.blockNum, 10)
        assertEquals(blockIdDetails.blockPrefix, 3034640669L)
    }
}