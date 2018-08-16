package com.memtrip.eos.abi.writer.compression

internal class NoCompression : Compression {

    override fun compress(uncompressedBytes: ByteArray): ByteArray = uncompressedBytes

    override fun decompress(compressedBytes: ByteArray): ByteArray = compressedBytes
}