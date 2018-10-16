# eos-http-rpc
An EOS client library that uses an OkHttpClient to make requests to the nodeos RPC HTTP API.

## Gradle
```
implementation ("com.memtrip.eos-jvm:eos-http-rpc:1.0.0-alpha14") {
    exclude group: "com.lambdaworks", module: "scrypt"
}
```

## Api
An instance of `Api` is used to start making requests to nodeos. The first argument,
`baseUrl: String` is the endpoint URL e.g; http://eos.greymass.com/.
(it must end in a forward slash). The second argument is an instance of `OkHttpClient`.

Create a new instance of `Api`
```kotlin
val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    .connectTimeout(3, TimeUnit.SECONDS)
    .readTimeout(3, TimeUnit.SECONDS)
    .writeTimeout(3, TimeUnit.SECONDS)
val api = Api("http://eos.greymass.com/", okHttpClient)
```

### ChainApi
The `ChainApi` interface contains all the network requests for the `chain/` resource.

chain/get_info
```kotlin
chainApi.getInfo().subscribe ({ response ->
    if (response.isSuccessful) {
        val info: Info = response.body()!!
    } else {
        val errorBody = response.errorBody()!!
    }
}, { error ->
    error.printStackTrace()
})
```

### WalletApi
The `WalletApi` interface contains all the network requests for the `wallet/` resource.

wallet/create
```kotlin
walletApi.create("wallet_name").subscribe ({ response ->
    if (response.isSuccessful) {
        val password: String = response.body()!!
    } else {
        val errorBody = response.errorBody()!!
    }
}, { error ->
    error.printStackTrace()
})
```

### HistoryApi
The `HistoryApi` interface contains all the network requests for the `history/` resource.

history/get_transaction
```kotlin
historyApi.getTransaction(GetTransaction("trx_id")).subscribe ({ response ->
    if (response.isSuccessful) {
        val historicTransaction: HistoricTransaction = response.body()!!
    } else {
        val errorBody = response.errorBody()!!
    }
}, { error ->
    error.printStackTrace()
})
```

### Integration tests
The `src/test` directory contains a full suite of integration tests. You will need to
run the setup scripts in [eos-dev-env](https://github.com/memtrip/eos-jvm/tree/master/eos-dev-env)
for a few of the tests to run successfully.
