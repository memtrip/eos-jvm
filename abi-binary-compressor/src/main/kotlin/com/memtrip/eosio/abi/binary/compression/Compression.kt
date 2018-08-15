package com.memtrip.eosio.abi.binary.compression

interface Compression {

    fun compress(uncompressedBytes: ByteArray): ByteArray

    fun decompress(compressedBytes: ByteArray): ByteArray
}