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

object SignatureFormatter {

    fun formatSignature(prefix: String, sig: String): String {
        return formatSignature(prefix, "K1", sig)
    }

    private fun formatSignature(prefix: String, signaturePrefix: String, sig: String): String {
        return if (signaturePrefix.isEmpty()) {
            prefix + sig
        } else {
            prefix + "_" + signaturePrefix + "_" + sig
        }
    }
}