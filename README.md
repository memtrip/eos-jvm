## eos-jvm
EOS libraries for the JVM, designed primarily for Android development. Our open source Android wallet app
[EOS REACH](https://github.com/memtrip/eosreach) serves as a blueprint for how other developers might want to utilise this SDK.

[![Travis ci](https://travis-ci.com/memtrip/eos-jvm.svg?branch=master)](https://travis-ci.com/memtrip/eos-jvm)

### [eos-chain-actions](https://github.com/memtrip/eos-jvm/tree/master/eos-chain-actions)
An EOS SDK for pushing actions to the EOS system contracts. The SDK utilises eos-jvm
to seamlessly handle transaction signing, byte writing and api requests.

### [eos-core](https://github.com/memtrip/eos-jvm/tree/master/eos-core)
An EOS client library containing the core building blocks required to interact with the EOS network.

### [eos-http-rpc](https://github.com/memtrip/eos-jvm/tree/master/eos-http-rpc)
An EOS client library that uses an OkHttpClient to make requests to the nodeos RPC HTTP API.

### [eos-abi-writer](https://github.com/memtrip/eos-jvm/tree/master/eos-abi-writer)
A local replacement of `abi_json_to_bin`, annotation processing is used to generate reliable Abi byte writing boilerplate code.

### [eos-dev-env](https://github.com/memtrip/eos-jvm/tree/master/eos-dev-env)
Scripts and config for initialising a development environment with test data.

### Credits
- [Join us on telegram](http://t.me/joinchat/JcIXl0x7wC9cRI5uF_EiQA)
- [Developed by memtrip.com](http://memtrip.com)
- Thank you to [swapnibble](https://github.com/swapnibble) for [EosCommander](https://github.com/playerone-id/EosCommander), an invaluable resource for anyone experimenting with EOS.
