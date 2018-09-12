package com.memtrip.eos.chain.actions.transaction

import com.memtrip.eos.core.crypto.EosPrivateKey
import java.util.*

data class TransactionContext(
    val authorizingAccountName: String,
    val authorizingPrivateKey: EosPrivateKey,
    val expirationDate: Date
)