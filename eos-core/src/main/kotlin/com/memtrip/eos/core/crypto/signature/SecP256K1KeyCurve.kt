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