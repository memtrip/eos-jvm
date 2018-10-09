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
package com.memtrip.eos.core.block

import com.memtrip.eos.core.hex.DefaultHexWriter
import com.memtrip.eos.core.hex.HexWriter

import org.bitcoinj.core.Utils
import java.math.BigInteger

data class BlockIdDetails(
    val blockId: String,
    val hexWriter: HexWriter = DefaultHexWriter(),
    val blockNum: Int = BigInteger(1, hexWriter.hexToBytes(blockId.substring(0, 8))).toInt(),
    val blockPrefix: Long = Utils.readUint32(hexWriter.hexToBytes(blockId.substring(16, 24)), 0)
)