package com.memtrip.eosio.abi.binary

interface ByteWriter {
    fun putName(value: String)
    fun putAccountName(value: String)
    fun putBlockNum(value: Int)
    fun putBlockPrefix(value: Long)
    fun putData(value: String)
    fun putTimestampMs(value: Long)
    fun putByte(value: Byte)
    fun putShort(value: Short)
    fun putInt(value: Int)
    fun putVariableUInt(value: Long)
    fun putLong(value: Long)
    fun putBytes(value: ByteArray)
    fun putString(value: String)
    fun putStringCollection(stringList: List<String>)

    fun toBytes(): ByteArray
    fun length(): Int
}