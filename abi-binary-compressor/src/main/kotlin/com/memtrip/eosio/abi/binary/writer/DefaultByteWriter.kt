package com.memtrip.eosio.abi.binary.writer

import com.memtrip.eosio.abi.binary.ByteWriter

class DefaultByteWriter(
    capacity: Int,
    private val nameWriter: NameWriter = NameWriter(),
    private val accountNameWriter: AccountNameWriter = AccountNameWriter(),
    private val hexWriter: DefaultHexWriter = DefaultHexWriter()
) : ByteWriter {

    private var buffer: ByteArray = ByteArray(capacity)
    private var index: Int = 0

    private fun ensureCapacity(capacity: Int) {
        if (buffer.size - index < capacity) {
            val temp = ByteArray(buffer.size * 2 + capacity)
            System.arraycopy(buffer, 0, temp, 0, index)
            buffer = temp
        }
    }

    override fun putName(value: String) {
        nameWriter.put(value, this)
    }

    override fun putAccountName(value: String) {
        accountNameWriter.put(value, this)
    }

    override fun putBlockNum(value: Int) {
        putShort((value and 0xFFFF).toShort())
    }

    override fun putBlockPrefix(value: Long) {
        putInt((value and -0x1).toInt())
    }

    override fun putData(value: String) {
        val dataAsBytes = hexWriter.hexToBytes(value)
        putVariableUInt(dataAsBytes.size.toLong())
        putBytes(dataAsBytes)
    }

    override fun putTimestampMs(value: Long) {
        putInt((value / 1000).toInt())
    }

    override fun putByte(value: Byte) {
        ensureCapacity(1);
        buffer[index++] = value
    }

    override fun putShort(value: Short) {
        ensureCapacity(2)
        buffer[index++] = (0xFF and value.toInt()).toByte()
        buffer[index++] = (0xFF and (value.toInt() shr 8)).toByte()
    }

    override fun putInt(value: Int) {
        ensureCapacity(4)
        buffer[index++] = (0xFF and value).toByte()
        buffer[index++] = (0xFF and (value shr 8)).toByte()
        buffer[index++] = (0xFF and (value shr 16)).toByte()
        buffer[index++] = (0xFF and (value shr 24)).toByte()
    }

    override fun putVariableUInt(value: Long) {
        var copy: Long = value
        do {
            var b: Byte = (copy and 0x7f).toByte()
            copy = copy shr 7
            b = (b.toInt() or ((if (copy > 0) 1 else 0) shl 7)).toByte()
            putByte(b)
        } while (copy != 0L)
    }

    override fun putLong(value: Long) {
        ensureCapacity(8)
        buffer[index++] = (0xFFL and value).toByte()
        buffer[index++] = (0xFFL and (value shr 8)).toByte()
        buffer[index++] = (0xFFL and (value shr 16)).toByte()
        buffer[index++] = (0xFFL and (value shr 24)).toByte()
        buffer[index++] = (0xFFL and (value shr 32)).toByte()
        buffer[index++] = (0xFFL and (value shr 40)).toByte()
        buffer[index++] = (0xFFL and (value shr 48)).toByte()
        buffer[index++] = (0xFFL and (value shr 56)).toByte()
    }

    override fun putBytes(value: ByteArray) {
        ensureCapacity(value.size)
        System.arraycopy(value, 0, buffer, index, value.size)
        index += value.size
    }

    override fun putString(value: String) {
        putVariableUInt(value.length.toLong())
        putBytes(value.toByteArray())
    }

    override fun putStringCollection(stringList: List<String>) {
        putVariableUInt(stringList.size.toLong())

        if (!stringList.isEmpty()) {
            for (string in stringList) {
                putString(string)
            }
        }
    }

    override fun toBytes(): ByteArray {
        val bytes = ByteArray(index)
        System.arraycopy(buffer, 0, bytes, 0, index)
        return bytes
    }

    override fun length(): Int = index
}