package com.memtrip.eos.http.rpc.utils

import java.util.Date
import java.util.Calendar

class Config {
    companion object {
        const val CHAIN_API_BASE_URL = "https://api.jungle.alohaeos.com:443/"
        const val MAINNET_API_BASE_URL = "https://eos.greymass.com/"
    }
}

fun transactionDefaultExpiry(): Date = with(Calendar.getInstance()) {
    set(Calendar.MINUTE, get(Calendar.MINUTE) + 2)
    this
}.time