# eos-core
An EOS client library containing the core building blocks required to interact
with the EOS network.

## Gradle
```
implementation ("com.memtrip.eos-jvm:eos-core:1.0.0-beta02") {
    exclude group: "com.lambdaworks", module: "scrypt"
}
```

## Key pairs
EOS keypairs are generated using ECKey from bitcoinj. Creating key instances is a blocking
operation, we recommend instantiating keys on a separate thread on memory
constrained systems.

Create a new keypair:
```kotlin
# generate new key pair
val eosPrivate = EosPrivateKey()
val eosPublicKey = eosPrivate.publicKey

# from encoded string
val fromEncodedPrivateKey = EosPrivateKey("5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3")

# from bytes
val fromBytesPrivateKey = EosPrivateKey(eosPrivate.bytes)
```

## Sign bytes with a private key
When a transaction is pushed to the EOS network, the packed transaction bytes must be
signed by an EOS private key.

Sign bytes with an EOS private key
```kotlin
val bytesToSign = ByteArray()
val privateKey = EosPrivateKey()
val signature: String = PrivateKeySigning().sign(bytesToSign, privateKey)
```

## Block Id details
Extract the block number and prefix from a block id.

```kotlin
val blockIdDetails = BlockIdDetails("0000000ac7619ca01df1e0b4964921020e772ceb7343ec51f65537cdbce192d3")
val blockNum: String = blockIdDetails.blockNum
val blockPrefix: String = blockIdDetails.blockPrefix
```

## Pretty system resources
System resources are provided by the EOS RPC api as Long types, the Pretty static
methods will format these system values into human readable formats.

```kotlin
val net: String = Pretty.net(4048)
val ram: String = Pretty.ram(5000)
val cpu: String = Pretty.cpu(1000)
```

## Hex
Transaction bytes are expected by the EOS RPC api as a hexadecimal string representation.

```kotlin
val hexWriter: HexWriter = DefaultHexWriter()
val networkBytes = ByteArray()
val packedTransaction: String = hexWriter.bytesToHex(networkBytes)
```
