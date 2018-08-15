package com.memtrip.eosio.abi.binary.compression

internal class NoCompression : Compression {

    override fun compress(uncompressedBytes: ByteArray): ByteArray = uncompressedBytes

    override fun decompress(compressedBytes: ByteArray): ByteArray = compressedBytes
}