package com.memtrip.eosio.abi.binary.bit

class BitUtils {

    companion object {
        fun uint32ToLong(buf: ByteArray, o: Int): Long {

            fun ffl(): Int = 0xFFL.toInt()

            var offset = o
            return (buf[offset++].toInt() and ffl() or (buf[offset++].toInt() and ffl() shl 8) or (buf[offset++].toInt() and ffl() shl 16)
                or (buf[offset].toInt() and ffl() shl 24)).toLong()
        }
    }
}