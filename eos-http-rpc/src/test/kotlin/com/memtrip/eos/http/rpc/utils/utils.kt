package com.memtrip.eos.http.rpc.utils

import java.util.Date
import java.util.Random
import java.util.Calendar
import kotlin.streams.asSequence

fun generateUniqueWalletName(): String {
    val source = "abcdefghijklmnopqrstuvwxyz"
    return Random().ints(12, 0, source.length)
            .asSequence()
            .map(source::get)
            .joinToString("")
}

class Config {
    companion object {
        const val WALLET_API_BASE_URL = "https://jungle2.cryptolions.io:443/"
        const val CHAIN_API_BASE_URL = "https://jungle2.cryptolions.io:443/"
        const val MAINNET_API_BASE_URL = "https://eos.greymass.com/"
    }
}

fun transactionDefaultExpiry(): Date = with(Calendar.getInstance()) {
    set(Calendar.MINUTE, get(Calendar.MINUTE) + 2)
    this
}.time