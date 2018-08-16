package com.memtrip.eos.abi.writer.compression

interface Compression {

    fun compress(uncompressedBytes: ByteArray): ByteArray

    fun decompress(compressedBytes: ByteArray): ByteArray
}