package com.memtrip.eos.http.rpc

import org.threeten.bp.LocalDateTime
import java.util.Calendar
import java.util.Random

fun generateUniqueWalletName(): String = (Random().nextInt(999999999 - 1) + 1).toString()

fun generateUniqueAccountName(): String {
    fun random(): String = (Random().nextInt(5 - 1) + 1).toString()
    return random() + random() + random() +
        random() + random() + random() +
        random() + random() + random() +
        random() + random() + random()
}

class Config {
    companion object {
        const val WALLET_API_BASE_URL = "http://localhost:8899/"
        const val CHAIN_API_BASE_URL = "http://localhost:8888/"
    }
}

fun Calendar.toLocalDateTime(): LocalDateTime = LocalDateTime.of(get(Calendar.YEAR), get(Calendar.MONTH) + 1, get(Calendar.DAY_OF_MONTH), get(Calendar.HOUR_OF_DAY), get(Calendar.MINUTE), get(Calendar.SECOND),
    get(Calendar.MILLISECOND) * 1000000)