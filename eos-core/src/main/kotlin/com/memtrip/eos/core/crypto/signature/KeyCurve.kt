package com.memtrip.eos.core.crypto.signature

import org.spongycastle.math.ec.ECCurve
import org.spongycastle.math.ec.ECPoint
import java.math.BigInteger

interface KeyCurve<T : ECCurve> {

    fun curve(): T

    fun G(): ECPoint

    fun n(): BigInteger

    fun h(): BigInteger
}