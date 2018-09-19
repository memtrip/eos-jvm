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