package com.memtrip.eos.chain.actions

import java.util.*

fun generateUniqueAccountName(): String {
    fun random(): String = (Random().nextInt(5 - 1) + 1).toString()
    return random() + random() + random() +
        random() + random() + random() +
        random() + random() + random() +
        random() + random() + random()
}

class Config {
    companion object {
        const val CHAIN_API_BASE_URL = "http://localhost:8888/"
    }
}

fun transactionDefaultExpiry(): Date = with (Calendar.getInstance()) {
    set(Calendar.MINUTE, get(Calendar.MINUTE) + 2)
    this
}.time