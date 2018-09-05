package com.memtrip.eos.http.aggregation.account

import com.memtrip.eos.abi.writer.compression.CompressionType
import com.memtrip.eos.core.block.BlockIdDetails
import com.memtrip.eos.core.crypto.EosPrivateKey
import com.memtrip.eos.core.crypto.EosPublicKey
import com.memtrip.eos.core.crypto.signature.PrivateKeySigning

import com.memtrip.eos.http.aggregation.TransactionResponse
import com.memtrip.eos.http.aggregation.account.actions.buyram.BuyRamArgs
import com.memtrip.eos.http.aggregation.account.actions.buyram.BuyRamBody
import com.memtrip.eos.http.aggregation.account.actions.delegatebw.DelegateBandwidthArgs
import com.memtrip.eos.http.aggregation.account.actions.delegatebw.DelegateBandwidthBody
import com.memtrip.eos.http.aggregation.account.actions.newaccount.NewAccountArgs
import com.memtrip.eos.http.aggregation.account.actions.newaccount.NewAccountBody

import com.memtrip.eos.http.rpc.ChainApi
import com.memtrip.eos.http.rpc.model.account.response.AccountKey
import com.memtrip.eos.http.rpc.model.account.response.AccountRequiredAuth
import com.memtrip.eos.http.rpc.model.signing.PushTransaction
import com.memtrip.eos.http.rpc.model.transaction.TransactionAuthorization
import com.memtrip.eos.http.rpc.model.transaction.request.Action
import com.memtrip.eos.http.rpc.model.transaction.request.SignedTransaction
import com.memtrip.eos.http.rpc.model.transaction.request.Transaction
import com.memtrip.eos.http.rpc.model.transaction.response.TransactionCommitted
import com.memtrip.eosio.abi.binary.gen.AbiBinaryGen
import io.reactivex.Single
import org.threeten.bp.LocalDateTime
import retrofit2.Response
import java.util.Arrays

class CreateAccount(
    private val chainApi: ChainApi
) {

    data class Args(
        val newAccountName: String,
        val quantity: Quantity,
        val ownerPublicKey: EosPublicKey,
        val activePublicKey: EosPublicKey,
        val authorizingAccountName: String,
        val authorizingPrivateKey: EosPrivateKey,
        val expirationDate: LocalDateTime) {

        data class Quantity(
            val ram: String,
            val net: String,
            val cpu: String)
    }

    fun createAccount(args: Args): Single<TransactionResponse<TransactionCommitted>> {
        return chainApi.getInfo().flatMap { info ->
            if (info.isSuccessful) {
                val transaction = transaction(
                    args,
                    BlockIdDetails(info.body()!!.head_block_id),
                    newAccountAbi(args),
                    buyRamAbi(args),
                    delegateRamAbi(args))

                val signature = PrivateKeySigning().sign(
                    AbiBinaryGen(CompressionType.NONE).squishSignedTransaction(
                        SignedTransaction(
                            info.body()!!.chain_id,
                            transaction,
                            emptyList()
                        )
                    ).toBytes(), args.authorizingPrivateKey)

                chainApi.pushTransaction(
                    PushTransaction(
                        Arrays.asList(signature),
                        "none",
                        "",
                        AbiBinaryGen(CompressionType.NONE).squishTransaction(transaction).toHex()))
            } else {
                Single.just(Response.error(info.code(), info.errorBody()!!))
            }
        }.map {
            TransactionResponse(
                it.isSuccessful,
                it.code(),
                it.body(),
                it.errorBody()?.string())
        }
    }

    private fun newAccountAbi(args: Args): String {
        return AbiBinaryGen(CompressionType.NONE).squishNewAccountBody(
            NewAccountBody(
                NewAccountArgs(
                    "eosio",
                    args.newAccountName,
                    AccountRequiredAuth(
                        1,
                        Arrays.asList(
                            AccountKey(
                                args.ownerPublicKey.toString(),
                                1)
                        ),
                        emptyList(),
                        emptyList()
                    ),
                    AccountRequiredAuth(
                        1,
                        Arrays.asList(
                            AccountKey(
                                args.activePublicKey.toString(),
                                1)
                        ),
                        emptyList(),
                        emptyList()
                    )
                )
            )
        ).toHex()
    }

    private fun buyRamAbi(args: Args): String {
        return AbiBinaryGen(CompressionType.NONE).squishBuyRamBody(
            BuyRamBody(
                BuyRamArgs(
                    args.authorizingAccountName,
                    args.newAccountName,
                    args.quantity.ram)
            )
        ).toHex()
    }

    private fun delegateRamAbi(args: Args): String {
        return AbiBinaryGen(CompressionType.NONE).squishDelegateBandwidthBody(
            DelegateBandwidthBody(
                "eosio",
                "delegatebw",
                DelegateBandwidthArgs(
                    args.authorizingAccountName,
                    args.newAccountName,
                    args.quantity.net,
                    args.quantity.cpu,
                    0
                )
            )
        ).toHex()
    }

    private fun transaction(
        args: Args,
        blockIdDetails: BlockIdDetails,
        newAccountBin: String,
        buyRamBin: String,
        delegateBandWidthBin: String
    ): Transaction {
        return Transaction(
            args.expirationDate,
            blockIdDetails.blockNum,
            blockIdDetails.blockPrefix,
            0,
            0,
            0,
            emptyList(),
            Arrays.asList(
                Action(
                    "eosio",
                    "newaccount",
                    Arrays.asList(TransactionAuthorization(
                        "eosio",
                        "active")
                    ),
                    newAccountBin
                ),
                Action(
                    "eosio",
                    "buyram",
                    Arrays.asList(TransactionAuthorization(
                        "eosio",
                        "active")
                    ),
                    buyRamBin
                ),
                Action(
                    "eosio",
                    "delegatebw",
                    Arrays.asList(TransactionAuthorization(
                        "eosio",
                        "active")
                    ),
                    delegateBandWidthBin
                )
            ),
            emptyList(),
            emptyList(),
            emptyList())
    }
}