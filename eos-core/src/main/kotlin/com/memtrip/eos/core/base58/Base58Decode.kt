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
package com.memtrip.eos.core.base58

import com.memtrip.eos.core.hash.RIPEMD160Digest
import com.memtrip.eos.core.utils.BytesWithChecksum

import org.bitcoinj.core.Base58
import org.bitcoinj.core.Utils
import java.util.Arrays

class Base58Decode {

    fun decode(base58Data: String): BytesWithChecksum {
        val data = Base58.decode(base58Data)
        val checksum = decodeChecksum(data)
        return BytesWithChecksum(Arrays.copyOfRange(data, 0, data.size - 4), checksum)
    }

    private fun decodeChecksum(data: ByteArray): Long {
        val hashData = ByteArray(data.size - 4)
        System.arraycopy(data, 0, hashData, 0, data.size - 4)

        val hashChecksum = Utils.readUint32(RIPEMD160Digest.hash(hashData), 0)
        val dataChecksum = Utils.readUint32(data, data.size - 4)

        if (hashChecksum != dataChecksum) {
            throw IllegalArgumentException("Invalid format, checksum mismatch")
        }

        return dataChecksum
    }
}