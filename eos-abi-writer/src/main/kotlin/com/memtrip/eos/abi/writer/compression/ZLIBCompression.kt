package com.memtrip.eos.abi.writer.compression

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.zip.DataFormatException
import java.util.zip.Deflater
import java.util.zip.Inflater

internal class ZLIBCompression : Compression {

    override fun compress(uncompressedBytes: ByteArray): ByteArray {
        val deflate = Deflater(Deflater.BEST_COMPRESSION)
        deflate.setInput(uncompressedBytes)

        val outputStream = ByteArrayOutputStream(uncompressedBytes.size)
        deflate.finish()
        val buffer = ByteArray(1024)
        while (!deflate.finished()) {
            val count = deflate.deflate(buffer) // returns the generated code... index
            outputStream.write(buffer, 0, count)
        }

        try {
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
            return uncompressedBytes
        }

        return outputStream.toByteArray()
    }

    override fun decompress(compressedBytes: ByteArray): ByteArray {
        val inflater = Inflater()
        inflater.setInput(compressedBytes)

        val outputStream = ByteArrayOutputStream(compressedBytes.size)
        val buffer = ByteArray(1024)

        try {
            while (!inflater.finished()) {
                val count = inflater.inflate(buffer)
                outputStream.write(buffer, 0, count)
            }
            outputStream.close()
        } catch (e: DataFormatException) {
            e.printStackTrace()
            return compressedBytes
        } catch (e: IOException) {
            e.printStackTrace()
            return compressedBytes
        }

        return outputStream.toByteArray()
    }
}