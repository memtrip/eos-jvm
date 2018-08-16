package com.memtrip.eos.core.crypto.signature

import com.memtrip.eos.core.hex.DefaultHexWriter

import org.spongycastle.math.ec.ECPoint
import org.spongycastle.math.ec.custom.sec.SecP256K1Curve

import java.math.BigInteger

class SecP256K1KeyCurve constructor(
    GxInHex: String = "79BE667EF9DCBBAC55A06295CE870B07029BFCDB2DCE28D959F2815B16F81798",
    GyInHex: String = "483ADA7726A3C4655DA4FBFC0E1108A8FD17B448A68554199C47D08FFB10D4B8",
    nInHex: String = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141",
    private val curve: SecP256K1Curve = SecP256K1Curve(),
    private val G: ECPoint = curve.decodePoint(DefaultHexWriter().hexToBytes("04$GxInHex$GyInHex")),
    private val n: BigInteger = BigInteger(nInHex, 16),
    private val h: BigInteger = n.shiftRight(1)
) : KeyCurve<SecP256K1Curve> {

    override fun curve(): SecP256K1Curve = curve

    override fun G(): ECPoint = G

    override fun n(): BigInteger = n

    override fun h(): BigInteger = h
}