package com.memtrip.eos.abi.writer.bytewriter

import java.nio.ByteBuffer
import java.nio.ByteOrder

class ByteArrayBuffer(
    capacity: Int,
    private var buffer: ByteArray = ByteArray(capacity),
    private var len: Int = 0
) {

    fun append(b: Byte): ByteArrayBuffer = appendToBuffer(b.toInt())

    fun append(v: Long): ByteArrayBuffer = with(ByteBuffer.allocate(java.lang.Long.BYTES)
        .order(ByteOrder.LITTLE_ENDIAN)
        .putLong(v).array()
    ) {
        append(this)
    }

    fun append(v: Int): ByteArrayBuffer = with(ByteBuffer.allocate(java.lang.Integer.BYTES)
        .order(ByteOrder.LITTLE_ENDIAN)
        .putInt(v)
        .array()
    ) {
        append(this)
    }

    fun append(v: Short): ByteArrayBuffer = with(ByteBuffer.allocate(java.lang.Short.BYTES)
        .order(ByteOrder.LITTLE_ENDIAN)
        .putShort(v)
        .array()
    ) {
        append(this)
    }

    fun append(v: Float): ByteArrayBuffer = with(ByteBuffer.allocate(java.lang.Float.BYTES)
        .order(ByteOrder.LITTLE_ENDIAN)
        .putFloat(v)
        .array()
    ) {
        append(this)
    }

    fun append(b: ByteArray, off: Int = 0, len: Int = b.size): ByteArrayBuffer = apply {
        if (off < 0 || off > b.size || len < 0 ||
            off + len < 0 || off + len > b.size) {
            throw IndexOutOfBoundsException("off: " + off + " len: " + len + " b.length: " + b.size)
        }
        if (len == 0) {
            return this
        }
        val newLen = this.len + len
        if (newLen > this.buffer.size) {
            expand(newLen)
        }
        System.arraycopy(b, off, this.buffer, this.len, len)
        this.len = newLen
    }

    private fun appendToBuffer(b: Int): ByteArrayBuffer = apply {
        val newLen = this.len + 1
        if (newLen > this.buffer.size) {
            expand(newLen)
        }
        this.buffer[this.len] = b.toByte()
        this.len = newLen
    }

    private fun expand(newLen: Int) {
        val newBuffer = ByteArray(Math.max(this.buffer.size shl 1, newLen))
        System.arraycopy(this.buffer, 0, newBuffer, 0, this.len)
        this.buffer = newBuffer
    }

    fun toByteArray(): ByteArray = ByteArray(len).apply {
        if (len > 0) {
            System.arraycopy(buffer, 0, this, 0, len)
        }
    }

    fun length(): Int = len
}