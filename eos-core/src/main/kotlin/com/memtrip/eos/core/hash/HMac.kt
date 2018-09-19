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
package com.memtrip.eos.core.hash

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object HMac {

    private val SHA256 = "SHA-256"
    private val SHA256_BLOCK_SIZE = 64

    fun hash(key: ByteArray, message: ByteArray): ByteArray {
        val digest: MessageDigest
        try {
            digest = MessageDigest.getInstance(SHA256)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        }

        return hmac(digest, key, message)
    }

    private fun hmac(digest: MessageDigest, value: ByteArray, message: ByteArray): ByteArray {
        var key = value

        if (key.size > SHA256_BLOCK_SIZE) {
            key = hash(digest, key)
        }

        if (key.size < SHA256_BLOCK_SIZE) {
            val temp = ByteArray(SHA256_BLOCK_SIZE)
            System.arraycopy(key, 0, temp, 0, key.size)
            key = temp
        }

        val o_key_pad = ByteArray(SHA256_BLOCK_SIZE)
        for (i in 0 until SHA256_BLOCK_SIZE) {
            o_key_pad[i] = (0x5c xor key[i].toInt()).toByte()
        }

        val i_key_pad = ByteArray(SHA256_BLOCK_SIZE)
        for (i in 0 until SHA256_BLOCK_SIZE) {
            i_key_pad[i] = (0x36 xor key[i].toInt()).toByte()
        }

        return hash(digest, o_key_pad, hash(digest, i_key_pad, message))
    }

    private fun hash(digest: MessageDigest, data: ByteArray): ByteArray {
        digest.reset()
        digest.update(data, 0, data.size)
        return digest.digest()
    }

    private fun hash(digest: MessageDigest, data1: ByteArray, data2: ByteArray): ByteArray {
        digest.reset()
        digest.update(data1, 0, data1.size)
        digest.update(data2, 0, data2.size)
        return digest.digest()
    }
}