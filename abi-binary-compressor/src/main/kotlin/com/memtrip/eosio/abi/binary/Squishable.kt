package com.memtrip.eosio.abi.binary

interface Squishable<T> {
    fun squish(obj: T, writer: ByteWriter)
}