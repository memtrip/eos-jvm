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
package com.memtrip.eos.core.crypto.signature

import com.memtrip.eos.core.base58.Base58Encode
import com.memtrip.eos.core.crypto.EosPrivateKey

import org.bitcoinj.core.Sha256Hash
import org.spongycastle.asn1.x9.X9IntegerConverter

class PrivateKeySigning {

    private val x9 = X9IntegerConverter()

    fun sign(digest: ByteArray, eosPrivateKey: EosPrivateKey): String {
        val signatureResult = ECSignatureProvider.sign(Sha256Hash.hash(digest), eosPrivateKey)
        return encodeSignature(signatureResult)
    }

    private fun encodeSignature(signatureResult: ECSignatureResult): String {
        if (signatureResult.recId < 0 || signatureResult.recId > 3) {
            throw IllegalStateException("Signature recovery id could not be retrieved, an invalid crypto was built.")
        }

        val sigData = ByteArray(65)

        val headerByte = signatureResult.recId + 27 + 4
        sigData[0] = headerByte.toByte()

        System.arraycopy(x9.integerToBytes(signatureResult.signature.r, 32), 0, sigData, 1, 32)
        System.arraycopy(x9.integerToBytes(signatureResult.signature.s, 32), 0, sigData, 33, 32)

        return Base58Encode.encodeSignature("SIG", sigData)
    }
}