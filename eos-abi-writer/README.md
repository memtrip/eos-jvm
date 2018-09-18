# eos-abi-writer
A local replacement of `abi_json_to_bin`, annotation processing is used to generate
reliable Abi byte writing boilerplate code.

## @Abi model
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
val abiBin = AbiBinaryGen(CompressionType.NONE).squishTransferArgs(transferArgs).toHex()
```

## Examples
See the [src/main/kotlin/com/memtrip/eos/chain/actions/transaction](https://github.com/memtrip/eos-jvm/eos-chain-actions/src/main/kotlin/com/memtrip/eos/chain/actions/transaction) package in [eos-chain-actions](https://github.com/memtrip/eos-jvm/eos-chain-actions)
for production examples. These examples are covered by a full suite of integration tests.
