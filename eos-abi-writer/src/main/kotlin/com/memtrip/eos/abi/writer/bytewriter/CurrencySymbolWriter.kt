package com.memtrip.eos.abi.writer.bytewriter

import com.memtrip.eos.abi.writer.ByteWriter

class CurrencySymbolWriter {

    fun put(precision: Int, symbol: CharSequence, writer: ByteWriter) {

        var result: Long = 0

        if (symbol.isEmpty()) {
            throw IllegalArgumentException("empty currency symbol string")
        }

        validateSymbolName(symbol)

        for (index in 0 until symbol.length) {
            val value = symbol[index].toLong()

            // check range 'A' to 'Z'
            if (value < 65 || value > 90) {
                throw IllegalArgumentException("invalid currency symbol string: $symbol")
            }

            result = result or (value shl 8 * (1 + index))
        }

        result = result or precision.toLong()

        writer.putLong(result)
    }

    private fun validateSymbolName(name: CharSequence): Boolean {
        for (index in 0 until name.length) {
            val value = name[index].toInt()

            // check range 'A' to 'Z'
            if (value < 97 || value > 122) {
                return false
            }
        }

        return true
    }

    private fun parseInt(content: String?, defaultValue: Int): Int {
        if (null == content) return defaultValue

        return try {
            Integer.parseInt(content)
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException("invalid currency formatting")
        }
    }

    companion object {
        private const val MAX_PRECISION = 18
    }
}