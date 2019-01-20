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
import org.bitcoinj.core.ECKey

import org.bitcoinj.core.Sha256Hash
import org.bitcoinj.core.Utils
import org.spongycastle.asn1.x9.X9IntegerConverter
import org.spongycastle.util.encoders.Base64
import java.nio.charset.Charset

class PrivateKeySigning {

    fun sign(digest: ByteArray, eosPrivateKey: EosPrivateKey): String {
        return signMessage(eosPrivateKey.key, digest)
    }

    private fun signMessage(ecKey: ECKey, digest: ByteArray): String {
        val hash = Sha256Hash.twiceOf(digest)
        val sig = ecKey.sign(hash, null)
        // Now we have to work backwards to figure out the recId needed to recover the signature.
        var recId = -1
        for (i in 0..3) {
            val k = ECKey.recoverFromSignature(i, sig, hash, ecKey.isCompressed)
            if (k != null && k.pubKeyPoint == ecKey.pubKeyPoint) {
                recId = i
                break
            }
        }
        if (recId == -1)
            throw RuntimeException("Could not construct a recoverable key. This should never happen.")
        val headerByte = recId + 27 + if (ecKey.isCompressed) 4 else 0
        val sigData = ByteArray(65)  // 1 header + 32 bytes for R + 32 bytes for S
        sigData[0] = headerByte.toByte()
        System.arraycopy(Utils.bigIntegerToBytes(sig.r, 32), 0, sigData, 1, 32)
        System.arraycopy(Utils.bigIntegerToBytes(sig.s, 32), 0, sigData, 33, 32)

        return Base58Encode.encodeSignature("SIG", sigData)
    }
}