package com.memtrip.eos.http.aggregation

import com.memtrip.eos.core.crypto.EosPrivateKey
import java.util.Date

data class AggregateContext(
    val authorizingAccountName: String,
    val authorizingPrivateKey: EosPrivateKey,
    val expirationDate: Date
)