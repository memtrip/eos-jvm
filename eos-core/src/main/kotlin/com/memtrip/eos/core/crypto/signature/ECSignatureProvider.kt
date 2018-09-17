package com.memtrip.eos.core.crypto.signature

import com.google.common.base.Preconditions
import com.memtrip.eos.core.crypto.EosPrivateKey
import com.memtrip.eos.core.crypto.EosPublicKey
import com.memtrip.eos.core.hash.HMac

import org.bitcoinj.core.ECKey
import org.bitcoinj.core.Sha256Hash
import org.spongycastle.asn1.x9.X9IntegerConverter
import org.spongycastle.math.ec.ECAlgorithms
import org.spongycastle.math.ec.ECCurve
import org.spongycastle.math.ec.ECPoint
import java.math.BigInteger
import java.util.Arrays

class ECSignatureProvider private constructor(
    private val hash: ByteArray,
    private val eosPrivateKey: EosPrivateKey,
    private val privateKeyAsBigInteger: BigInteger = eosPrivateKey.bigInteger,
    private val keyCurve: SecP256K1KeyCurve = eosPrivateKey.keyCurve,
    private val hashAsBigInteger: BigInteger = BigInteger(1, hash)
) {

    private fun generate(): ECSignatureResult {
        var components: Array<BigInteger>
        var nonce = 0

        do {
            components = deterministicGenerateK(
                keyCurve,
                hash,
                hashAsBigInteger,
                privateKeyAsBigInteger,
                nonce++)

            if (components[1] > keyCurve.h()) {
                components[1] = keyCurve.n().subtract(components[1])
            }
        } while (!validComponentLength(components))

        return setRecId(hash, eosPrivateKey, ECKey.ECDSASignature(components[0], components[1]))
    }

    private fun validComponentLength(components: Array<BigInteger>): Boolean {
        return components[0].toByteArray().size == LENGTH && components[1].toByteArray().size == LENGTH
    }

    private fun deterministicGenerateK(
        keyCurve: KeyCurve<*>,
        hashIn: ByteArray,
        hashAsBigInteger: BigInteger,
        privateKey: BigInteger,
        nonce: Int
    ): Array<BigInteger> {

        var hash = hashIn

        if (nonce > 0) {
            val nonceByteArray = BigInteger.valueOf(nonce.toLong()).toByteArray()
            hash = Sha256Hash.hashTwice(
                hash, 0, hash.size,
                nonceByteArray, 0, nonceByteArray.size)
        }

        val dBytes = privateKey.toByteArray()

        var v = ByteArray(32)
        Arrays.fill(v, 0x01.toByte())

        var k = ByteArray(32)
        Arrays.fill(k, 0x00.toByte())

        val bwD = ByteWriter(32 + 1 + 32 + 32)
        bwD.putBytes(v)
        bwD.putByte(0x00.toByte())
        bwD.putBytes(dBytes)
        bwD.putBytes(hash)
        k = HMac.hash(k, bwD.toBytes())

        v = HMac.hash(k, v)

        val bwF = ByteWriter(32 + 1 + 32 + 32)
        bwF.putBytes(v)
        bwF.putByte(0x01.toByte())
        bwF.putBytes(dBytes)
        bwF.putBytes(hash)
        k = HMac.hash(k, bwF.toBytes())

        v = HMac.hash(k, v)

        v = HMac.hash(k, v)

        return createSignature(BigInteger(1, v), v, k, keyCurve, hashAsBigInteger, privateKey)
    }

    private fun createSignature(
        k1: BigInteger,
        vIn: ByteArray,
        kIn: ByteArray,
        keyCurve: KeyCurve<*>,
        hashAsBigInteger: BigInteger,
        privateKey: BigInteger
    ): Array<BigInteger> {

        var v = vIn
        var k = kIn

        val q = multiply(keyCurve.G(), k1)
        val r = q.normalize().xCoord.toBigInteger().mod(keyCurve.n())
        val s = k1.modInverse(keyCurve.n())
            .multiply(hashAsBigInteger.add(privateKey.multiply(r)))
            .mod(keyCurve.n())

        if (k1.signum() <= 0 || k1 >= keyCurve.n() || !valid(q, r, s)) {
            val bwH = ByteWriter(32 + 1)
            bwH.putBytes(v)
            bwH.putByte(0x00.toByte())
            k = HMac.hash(k, bwH.toBytes())
            v = HMac.hash(k, v)
            v = HMac.hash(k, v)

            createSignature(BigInteger(v), v, k, keyCurve, hashAsBigInteger, privateKey)
        }

        return arrayOf(r, s)
    }

    private fun multiply(p: ECPoint, k: BigInteger): ECPoint {
        val h = k.multiply(BigInteger.valueOf(3))

        val neg = p.negate()
        var R = p

        for (i in h.bitLength() - 2 downTo 1) {
            R = R.twice()

            val hBit = h.testBit(i)
            val eBit = k.testBit(i)

            if (hBit != eBit) {
                R = R.add(if (hBit) p else neg)
            }
        }

        return R
    }

    private fun valid(q: ECPoint, r: BigInteger, s: BigInteger): Boolean {
        return !q.isInfinity && r.signum() != 0 && s.signum() != 0
    }

    companion object {

        private const val LENGTH = 32

        fun sign(hash: ByteArray, eosPrivateKey: EosPrivateKey): ECSignatureResult {
            return ECSignatureProvider(hash, eosPrivateKey).generate()
        }

        private fun setRecId(hash: ByteArray, eosPrivateKey: EosPrivateKey, signature: ECKey.ECDSASignature): ECSignatureResult {

            val pubKey = eosPrivateKey.publicKey

            var recId = -1

            for (i in 0..3) {
                val recovered = recoverPubKey(eosPrivateKey.keyCurve, hash, signature, i)
                if (pubKey == recovered) {
                    recId = i
                    break
                }
            }

            if (recId < 0) {
                throw IllegalStateException("could not find recid. Was this data signed with this key?")
            }

            return ECSignatureResult(signature, recId)
        }

        private fun recoverPubKey(keyCurve: SecP256K1KeyCurve, messageSigned: ByteArray, signature: ECKey.ECDSASignature, recId: Int): EosPublicKey? {

            Preconditions.checkArgument(recId >= 0, "recId must be positive")
            Preconditions.checkArgument(signature.r >= BigInteger.ZERO, "r must be positive")
            Preconditions.checkArgument(signature.s >= BigInteger.ZERO, "s must be positive")
            Preconditions.checkNotNull(messageSigned)

            val n = keyCurve.n()
            val i = BigInteger.valueOf(recId.toLong() / 2)
            val x = signature.r.add(i.multiply(n))

            val curve = keyCurve.curve()
            val prime = curve.q
            if (x >= prime) {
                return null
            }

            val R = decompressKey(curve, x, recId and 1 == 1)

            if (!R.multiply(n).isInfinity)
                return null

            val e = BigInteger(1, messageSigned)

            val eInv = BigInteger.ZERO.subtract(e).mod(n)
            val rInv = signature.r.modInverse(n)
            val srInv = rInv.multiply(signature.s).mod(n)
            val eInvrInv = rInv.multiply(eInv).mod(n)
            var q = ECAlgorithms.sumOfTwoMultiplies(keyCurve.G(), eInvrInv, R, srInv) //  Secp256k1Param.G, eInvrInv, R, srInv);

            q = curve.createPoint(q.x.toBigInteger(), q.y.toBigInteger(), true)

            return EosPublicKey(q.encoded)
        }

        // org.bitcoinj.core.ECKey
        private fun decompressKey(param: ECCurve, xBN: BigInteger, yBit: Boolean): ECPoint {
            val x9 = X9IntegerConverter()
            val compEnc = x9.integerToBytes(xBN, 1 + x9.getByteLength(param))
            compEnc[0] = (if (yBit) 0x03 else 0x02).toByte()
            return param.decodePoint(compEnc)
        }
    }

    class ByteWriter(
        capacity: Int
    ) {
        private var buffer: ByteArray = ByteArray(capacity)
        private var index: Int = 0

        private fun ensureCapacity(capacity: Int) {
            if (buffer.size - index < capacity) {
                val temp = ByteArray(buffer.size * 2 + capacity)
                System.arraycopy(buffer, 0, temp, 0, index)
                buffer = temp
            }
        }

        fun putByte(value: Byte) {
            ensureCapacity(1)
            buffer[index++] = value
        }

        fun putBytes(value: ByteArray) {
            ensureCapacity(value.size)
            System.arraycopy(value, 0, buffer, index, value.size)
            index += value.size
        }

        fun toBytes(): ByteArray {
            val bytes = ByteArray(index)
            System.arraycopy(buffer, 0, bytes, 0, index)
            return bytes
        }
    }
}