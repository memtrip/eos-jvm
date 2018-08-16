package com.memtrip.eos.abi.writer.bytewriter

import com.memtrip.eos.abi.writer.ByteWriter
import java.util.regex.Pattern

class AssetWriter(
    private val currencySymbolWriter: CurrencySymbolWriter = CurrencySymbolWriter()
) {

    fun put(asset: String, writer: ByteWriter) {

        val value = asset.trim()

        val pattern = Pattern.compile("^([0-9]+)\\.?([0-9]*)([ ][a-zA-Z0-9]{1,7})?$")
        val matcher = pattern.matcher(value)

        if (matcher.find()) {
            val beforeDotVal = matcher.group(1)
            val afterDotVal = matcher.group(2)

            val symbol = if (matcher.group(3).isEmpty()) null else matcher.group(3).trim()

            val amount = (beforeDotVal + afterDotVal).toLong()

            writer.putLong(amount)

            if (symbol != null) {
                currencySymbolWriter.put(afterDotVal.length, symbol, writer)
            } else {
                writer.putLong(0)
            }
        } else {
            throw IllegalArgumentException("invalid asset format")
        }
    }
}