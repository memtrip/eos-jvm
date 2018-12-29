package com.memtrip.eos.chain.actions

import com.memtrip.eos.chain.actions.transaction.TransactionContext
import com.memtrip.eos.chain.actions.transaction.account.CreateAccountChain
import com.memtrip.eos.chain.actions.transaction.transfer.TransferChain
import com.memtrip.eos.core.crypto.EosPrivateKey
import com.memtrip.eos.http.rpc.ChainApi
import com.memtrip.eos.http.rpc.model.transaction.response.TransactionCommitted
import io.reactivex.Single

class SetupTransactions(
    private val chainApi: ChainApi
) {

    fun createAccount(
        accountName: String,
        privateKey: EosPrivateKey
    ): Single<ChainResponse<TransactionCommitted>> {

        val signatureProviderPrivateKey = EosPrivateKey("5HvDsbgjH574GALj5gRcnscMfAGBQD9JSWn3sHFsD7bNrkqXqpr")

        return CreateAccountChain(chainApi).createAccount(
            CreateAccountChain.Args(
                accountName,
                CreateAccountChain.Args.Quantity(
                3048,
                "0.1000 EOS",
                "1.0000 EOS"),
                privateKey.publicKey,
                privateKey.publicKey,
                true
            ),
            TransactionContext(
                "memtripissue",
                signatureProviderPrivateKey,
                transactionDefaultExpiry()
            )
        )
    }

    fun transfer(to: String): Single<ChainResponse<TransactionCommitted>> {

        val signatureProviderPrivateKey = EosPrivateKey("5HvDsbgjH574GALj5gRcnscMfAGBQD9JSWn3sHFsD7bNrkqXqpr")

        return TransferChain(chainApi).transfer(
            "eosio.token",
            TransferChain.Args(
                "memtripissue",
                to,
                "0.2000 EOS",
                "eos-swift test suite -> transfer"
            ),
            TransactionContext(
                "memtripissue",
                signatureProviderPrivateKey,
                transactionDefaultExpiry()
            )
        )
    }
}