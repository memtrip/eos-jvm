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

import com.memtrip.eos.abi.writer.ByteWriter

class NameWriter {

    fun put(name: String, writer: ByteWriter) {

        val len = name.length
        var value: Long = 0

        for (i in 0..MAX_NAME_IDX) {
            var c: Long = 0

            if (i < len && i <= MAX_NAME_IDX) {
                c = charToSymbol(name[i]).toLong()
            }

            if (i < MAX_NAME_IDX) {
                c = c and 0x1f
                c = c shl (64 - 5 * (i + 1))
            } else {
                c = c and 0x0f
            }

            value = value or c
        }

        writer.putLong(value)
    }

    private fun charToSymbol(c: Char): Byte {
        if (c in 'a'..'z') {
            return (c - 'a' + 6).toByte()
        }
        return if (c in '1'..'5') (c - '1' + 1).toByte() else 0.toByte()
    }

    companion object {
        const val MAX_NAME_IDX = 12
    }
}