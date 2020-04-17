# eos-abi-writer

A local replacement of `abi_json_to_bin`, annotation processing is used to generate
reliable Abi byte writing boilerplate code.

## Gradle

```
implementation ("com.memtrip.eos-jvm:eos-abi-writer:1.0.0-beta04") {
   exclude group: "com.lambdaworks", module: "scrypt"
}
kapt 'com.memtrip.eos-jvm:eos-abi-writer-preprocessor:1.0.0-beta04'
```

## Writer

Annotate an interface with @AbiWriter, this will tell the annotation preprocessor what package to generate the code in
and will generate an AbiBinaryGen\${interface_name} class.

```kotlin
@AbiWriter
interface TransferWriter
```

## Model

Simply annotate a data class or pojo with `@Abi`, and use on of the many `@*Compress`
annotations on the getters that you would like to write to abi bytes for.

```kotlin
@Abi
data class TransferArgs(
    val from: String,
    val to: String,
    val quantity: String,
    val memo: String
) {

    val getFrom: String
        @AccountNameCompress get() = from

    val getTo: String
        @AccountNameCompress get() = to

    val getQuantity: String
        @AssetCompress get() = quantity

    val getMemo: String
        @StringCompress get() = memo
}
```

## Code generation

An AbiBinaryGen class is generated containing a squish method for each of the
classes generated with `@Abi`. Calling the `toHex()` method on the result of squish
will create a hexadecimal string representation of the bytes. This string is the correct
format for both `abiBin` and `packed_trnx`.

```kotlin
val transferArgs = TransferArgs("memtripissue", "memtripblock", "0.1000 EOS", "issue.")
val abiBin = AbiBinaryGenTransferWriter(CompressionType.NONE).squishTransferArgs(transferArgs).toHex()
```

## Byte writer annotations

Annotate the getters of Abi data classes with any of the following annotations to generate boiler plate.

`@NameCompress`
An EOS name string [a-z1-5]

`@AccountNameCompress`
An EOS account name string

`@BlockNumCompress`

`@BlockPrefixCompress`

`@PublicKeyCompress`
EOS public key string

`@AssetCompress`
An EOS asset string e.g; 0.1000 EOS

`@ChainIdCompress`
SHA256 hash string

`@ByteCompress`
Type of Byte

`@ShortCompress`
Type of Short

`@IntCompress`
Type of Int

`@LongCompress`
Type of Long

`@BytesCompress`
Type of ByteArray

`@StringCompress`
Type of String

`@StringCollectionCompress`
Type of List<String>

`@ChildCompress`
A child object that is annotated with @Abi

`@CollectionCompress`
A collection of objects annotated with @Abi

`@AccountNameCollectionCompress`
A collection of account names in a List<String> type

`@HexCollectionCompress`
`@DataCompress`
`@TimestampCompress`
`@VariableUIntCompress`

## Examples

See the [src/main/kotlin/com/memtrip/eos/chain/actions/transaction](https://github.com/memtrip/eos-jvm/tree/master/eos-chain-actions/src/test/kotlin/com/memtrip/eos/chain/actions/transaction) package in [eos-chain-actions](https://github.com/memtrip/eos-jvm/eos-chain-actions)
for production examples. These examples are covered by a full suite of integration tests.
