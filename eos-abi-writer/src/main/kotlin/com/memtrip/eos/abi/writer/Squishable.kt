package com.memtrip.eos.abi.writer

interface Squishable<T> {
    fun squish(obj: T, writer: ByteWriter)
}