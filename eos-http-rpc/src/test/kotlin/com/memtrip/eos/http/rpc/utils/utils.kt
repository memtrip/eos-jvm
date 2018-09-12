package com.memtrip.eos.http.rpc.utils

import java.util.Date
import java.util.Random
import java.util.Calendar

fun generateUniqueWalletName(): String = (Random().nextInt(999999999 - 1) + 1).toString()

class Config {
    companion object {
        const val WALLET_API_BASE_URL = "http://localhost:8899/"
        const val CHAIN_API_BASE_URL = "http://localhost:8888/"
    }
}

fun transactionDefaultExpiry(): Date = with (Calendar.getInstance()) {
    set(Calendar.MINUTE, get(Calendar.MINUTE) + 2)
    this
}.time