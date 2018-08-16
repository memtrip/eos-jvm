package com.memtrip.eos.abi.writer.bytewriter

import com.memtrip.eos.abi.writer.ByteWriter
import com.memtrip.eos.core.crypto.EosPublicKey

class PublicKeyWriter {

    fun put(publicKey: EosPublicKey, writer: ByteWriter) {
        writer.putVariableUInt(type(publicKey))
        writer.putBytes(publicKey.bytes)
    }

    private fun type(publicKey: EosPublicKey): Long = if (publicKey.isCurveParamK1) {
        PACK_VAL_CURVE_PARAM_TYPE_K1.toLong()
    } else {
        PACK_VAL_CURVE_PARAM_TYPE_R1.toLong()
    }

    companion object {
        private const val PACK_VAL_CURVE_PARAM_TYPE_K1: Byte = 0
        private const val PACK_VAL_CURVE_PARAM_TYPE_R1: Byte = 1
    }
}