# eos-chain-actions
An EOS SDK for pushing actions to the EOS system contracts. The SDK utilises eos-jvm
to seamlessly handle transaction signing, byte writing and api requests.

## Gradle
```
implementation com.memtrip.eos-jvm:eos-chain-actions:1.0.0-alpha02
```

## Supported actions
- CreateAccount
- BuyRam
- SellRam
- DelegateBandwidth
- UndelegateBandwidth
- Transfer
- Vote

## Examples
See src/test for a full suite of integration tests. The setup scripts in
[eos-dev-env](https://github.com/memtrip/eos-jvm/eos-dev-env) need to be ran
before the tests will work.
