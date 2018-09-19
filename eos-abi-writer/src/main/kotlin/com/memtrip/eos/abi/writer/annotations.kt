package com.memtrip.eos.abi.writer

@Target(AnnotationTarget.CLASS)
annotation class AbiWriter

@Target(AnnotationTarget.CLASS)
annotation class Abi

@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class NameCompress

@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class AccountNameCompress

@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class BlockNumCompress

@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class BlockPrefixCompress

@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class PublicKeyCompress

@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class AssetCompress

@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class ChainIdCompress

@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class DataCompress

@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class TimestampCompress

@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class VariableUIntCompress

@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class ByteCompress

@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class ShortCompress

@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class IntCompress

@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class LongCompress

@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class BytesCompress

@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class StringCompress

@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class StringCollectionCompress

@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class ChildCompress

@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class CollectionCompress

@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class HexCollectionCompress

@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class AccountNameCollectionCompress