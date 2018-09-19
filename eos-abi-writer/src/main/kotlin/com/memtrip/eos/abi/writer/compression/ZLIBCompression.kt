/**
 * Copyright 2013-present memtrip LTD.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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