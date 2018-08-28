package com.memtrip.eos.http.rpc.model.transaction.response

data class TransactionCommitted(
    val transaction_id: String,
    val processed: TransactionProcessed)