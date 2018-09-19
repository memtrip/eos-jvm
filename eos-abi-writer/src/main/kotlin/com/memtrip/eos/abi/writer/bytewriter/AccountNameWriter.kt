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

class AccountNameWriter {

    fun put(name: String, writer: ByteWriter) {

        if (name.length > MAX_LENGTH) {
            throw IllegalArgumentException("Account name cannot be more than 12 characters. => $name")
        }

        if ((name.indexOf(ILLEGAL_CHARACTER) >= 0) && !name.startsWith("eosio.")) {
            throw IllegalArgumentException("Account name cannot contain '.' or start with 'eosio'. => $name")
        }

        writer.putName(name)
    }

    companion object {
        const val MAX_LENGTH = 12
        const val ILLEGAL_CHARACTER = '.'
    }
}