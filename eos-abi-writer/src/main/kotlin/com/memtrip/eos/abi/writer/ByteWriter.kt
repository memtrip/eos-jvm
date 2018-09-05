package com.memtrip.eos.abi.writer

import com.memtrip.eos.core.crypto.EosPublicKey

interface ByteWriter {
    fun putName(value: String)
    fun putAccountName(value: String)
    fun putBlockNum(value: Int)
    fun putBlockPrefix(value: Long)
    fun putPublicKey(value: EosPublicKey)
    fun putAsset(value: String)
    fun putChainId(value: String)
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
    fun putHexCollection(stringList: List<String>)
    fun putAccountNameCollection(accountNameList: List<String>)

    fun toBytes(): ByteArray
    fun length(): Int
}