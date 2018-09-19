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
package com.memtrip.eos.abi.writer.bytewriter

import com.memtrip.eos.core.hex.DefaultHexWriter
import com.memtrip.eos.core.hex.HexWriter
import com.memtrip.eos.abi.writer.ByteWriter
import org.bitcoinj.core.Sha256Hash

class HexCollectionWriter(
    private val hexWriter: HexWriter = DefaultHexWriter()
) {

    fun put(hexList: List<String>, writer: ByteWriter) {
        writer.putBytes(hexCollectionBytes(hexList))
    }

    private fun hexCollectionBytes(hexList: List<String>): ByteArray {
        if (hexList.isEmpty()) {
            return ZERO_HASH
        }

        val writer = DefaultByteWriter(255)

        writer.putVariableUInt(hexList.size.toLong())

        for (string in hexList) {
            val stringBytes = hexWriter.hexToBytes(string)
            writer.putVariableUInt(stringBytes.size.toLong())
            writer.putBytes(stringBytes)
        }

        return Sha256Hash.hash(writer.toBytes())
    }

    companion object {
        private const val HASH_LENGTH = 32
        private val ZERO_HASH = ByteArray(HASH_LENGTH)
    }
}