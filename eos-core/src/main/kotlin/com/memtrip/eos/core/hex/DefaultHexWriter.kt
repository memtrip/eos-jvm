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
package com.memtrip.eos.core.hex

class DefaultHexWriter : HexWriter {

    override fun bytesToHex(bytes: ByteArray): String {
        return bytesToHex(bytes, 0, bytes.size, null)
    }

    override fun bytesToHex(bytes: ByteArray, offset: Int, length: Int, separator: String?): String {
        val result = StringBuilder()
        for (i in 0 until length) {
            val unsignedByte = bytes[i + offset].toInt() and 0xff

            if (unsignedByte < 16) {
                result.append("0")
            }

            result.append(Integer.toHexString(unsignedByte))
            if (separator != null && i + 1 < length) {
                result.append(separator)
            }
        }
        return result.toString()
    }

    override fun hexToBytes(hex: String): ByteArray {
        if (hex.length % 2 != 0) {
            throw RuntimeException("Assertion failure: Hex must contain an even amount of characters")
        }
        val hexArray = hex.toCharArray()
        val length = hexArray.size / 2
        val raw = ByteArray(length)
        for (i in 0 until length) {
            val high = Character.digit(hexArray[i * 2], 16)
            val low = Character.digit(hexArray[i * 2 + 1], 16)
            if (high < 0 || low < 0) {
                throw RuntimeException("Assertion failure: Invalid hex digit " + hexArray[i * 2] + hexArray[i * 2 + 1])
            }
            var value = high shl 4 or low
            if (value > 127)
                value -= 256
            raw[i] = value.toByte()
        }
        return raw
    }
}