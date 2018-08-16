package com.memtrip.eos.abi.writer.compression

class CompressionFactory(private val compressionType: CompressionType) {

    fun create(): Compression = when (compressionType) {
        CompressionType.NONE -> NoCompression()
        CompressionType.ZLIB -> ZLIBCompression()
    }
}