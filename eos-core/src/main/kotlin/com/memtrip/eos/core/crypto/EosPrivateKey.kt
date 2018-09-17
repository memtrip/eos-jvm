package com.memtrip.eos.core.crypto

import com.memtrip.eos.core.crypto.signature.SecP256K1KeyCurve
import org.bitcoinj.core.Base58
import org.bitcoinj.core.ECKey
import org.bitcoinj.core.Sha256Hash
import java.math.BigInteger
import java.util.Arrays

class EosPrivateKey internal constructor(private val key: ECKey) {

    val publicKey: EosPublicKey = EosPublicKey(key.pubKey)
    val keyCurve: SecP256K1KeyCurve = SecP256K1KeyCurve()

    val bytes: ByteArray
        get() = key.privKeyBytes

    val bigInteger: BigInteger
        get() = key.privKey

    constructor() : this(ECKey())

    constructor(base58: String) : this(getBase58Bytes(base58))

    constructor(bytes: ByteArray) : this(ECKey.fromPrivate(bytes))

    override fun toString(): String {
        val privateKeyBytes = bytes
        val resultWIFBytes = ByteArray(1 + 32 + 4)
        resultWIFBytes[0] = 0x80.toByte()
        System.arraycopy(privateKeyBytes, if (privateKeyBytes.size > 32) 1 else 0, resultWIFBytes, 1, 32)
        val hash = Sha256Hash.hashTwice(resultWIFBytes, 0, 33)
        System.arraycopy(hash, 0, resultWIFBytes, 33, 4)
        return Base58.encode(resultWIFBytes)
    }

    companion object {
        private fun getBase58Bytes(base58: String): ByteArray {
            if (base58.split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().size == 1) {
                val data = Base58.decode(base58)

                val checkOne = Sha256Hash.hash(data, 0, data.size - 4)
                val checkTwo = Sha256Hash.hash(checkOne)

                if (equalsFromOffset(checkTwo, data, data.size - 4) || equalsFromOffset(checkOne, data, data.size - 4)) {

                    val keyBytes = Arrays.copyOfRange(data, 1, data.size - 4)

                    if (keyBytes.size < 5) {
                        throw IllegalArgumentException("Invalid private key length.")
                    }
                    return keyBytes
                }

                throw IllegalArgumentException("Invalid format, checksum mismatch")
            } else {
                throw IllegalArgumentException("Invalid private format, expecting a prefix")
            }
        }

        private fun equalsFromOffset(mHashBytes: ByteArray, toCompareData: ByteArray?, offsetInCompareData: Int): Boolean {
            if (toCompareData == null ||
                offsetInCompareData < 0 ||
                mHashBytes.size <= 4 ||
                toCompareData.size <= offsetInCompareData) {

                return false
            }

            for (i in 0..3) {
                if (mHashBytes[i] != toCompareData[offsetInCompareData + i]) {
                    return false
                }
            }

            return true
        }
    }
}