/**
 * Copyright 2013-present memtrip LTD.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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