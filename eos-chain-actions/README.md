# eos-chain-actions
An EOS SDK for pushing actions to the EOS system contracts. This high level abstraction composes the other eos-jvm modules to seamlessly handle transaction signing, byte writing and api requests.

## Gradle
```
implementation ("com.memtrip.eos-jvm:eos-chain-actions:1.0.0-beta02") {
 exclude group: "com.lambdaworks", module: "scrypt"
}
```

## Supported actions
- CreateAccount
- BuyRam
- SellRam
- DelegateBandwidth
- UndelegateBandwidth
- Transfer
- Vote
- Refund

## Examples
See src/test for a full suite of integration tests.
