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
package com.memtrip.eos.core.crypto

import com.memtrip.eos.core.base58.Base58Decode
import com.memtrip.eos.core.base58.Base58Encode
import com.memtrip.eos.core.hash.RIPEMD160Digest
import com.memtrip.eos.core.utils.BytesWithChecksum

import org.bitcoinj.core.ECKey
import org.bitcoinj.core.Utils
import java.util.Arrays

class EosPublicKey {

    private val ecKey: ECKey
    private val checkSum: Long
    private val base58: String

    private val base58Encode = Base58Encode()

    val bytes: ByteArray
        get() = ecKey.pubKey

    @Suppress("unused")
    val isCurveParamK1: Boolean
        get() = true

    constructor(bytes: ByteArray) {
        this.ecKey = ECKey.fromPublicOnly(Arrays.copyOf(bytes, 33))
        this.checkSum = Utils.readUint32(RIPEMD160Digest.hash(ecKey.pubKey), 0)
        this.base58 = base58Encode.encodeKey(PREFIX, ecKey.pubKey)
    }

    constructor(base58: String) {
        val bytesWithChecksum = bytesFromBase58(base58)
        this.ecKey = ECKey.fromPublicOnly(bytesWithChecksum.bytes)
        this.checkSum = bytesWithChecksum.checkSum
        this.base58 = base58
    }

    internal constructor(ecKey: ECKey) {
        this.ecKey = ecKey
        this.checkSum = Utils.readUint32(RIPEMD160Digest.hash(ecKey.pubKey), 0)
        this.base58 = base58Encode.encodeKey(PREFIX, ecKey.pubKey)
    }

    override fun toString(): String {
        return base58
    }

    override fun hashCode(): Int {
        return (checkSum and 0xFFFFFFFFL).toInt()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        return if (null == other || javaClass != other.javaClass) false else bytes.contentEquals((other as EosPublicKey).bytes)
    }

    companion object {
        private const val PREFIX = "EOS"

        private fun bytesFromBase58(base58: String): BytesWithChecksum {
            val parts = base58.split("_")

            return if (base58.startsWith(PREFIX)) {
                if (parts.size == 1) {
                    Base58Decode().decode(base58.substring(PREFIX.length))
                } else {
                    throw IllegalArgumentException("Unsupported format: $base58")
                }
            } else {
                throw IllegalArgumentException("Unsupported key type.")
            }
        }
    }
}