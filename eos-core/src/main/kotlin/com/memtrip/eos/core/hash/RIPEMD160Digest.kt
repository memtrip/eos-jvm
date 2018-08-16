package com.memtrip.eos.core.hash

class RIPEMD160Digest {

    companion object {
        fun hash(data: ByteArray): ByteArray {
            val digest = org.spongycastle.crypto.digests.RIPEMD160Digest()
            digest.update(data, 0, data.size)
            val out = ByteArray(20)
            digest.doFinal(out, 0)
            return out
        }
    }
}