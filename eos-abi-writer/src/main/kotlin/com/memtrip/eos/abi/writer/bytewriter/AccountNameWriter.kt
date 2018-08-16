package com.memtrip.eos.abi.writer.bytewriter

import com.memtrip.eos.abi.writer.ByteWriter

class AccountNameWriter {

    fun put(name: String, writer: ByteWriter) {

        if (name.length > MAX_LENGTH) {
            throw IllegalArgumentException("Account name cannot be more than 12 characters. ($name)")
        }

        if ((name.indexOf(ILLEGAL_CHARACTER) >= 0) && !name.startsWith("eosio.")) {
            throw IllegalArgumentException("Account name cannot contain '.' or start with 'eosio'. ($name)")
        }

        writer.putName(name)
    }

    companion object {
        const val MAX_LENGTH = 12
        const val ILLEGAL_CHARACTER = '.'
    }
}