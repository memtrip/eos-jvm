package com.memtrip.eos.http.rpc.model.history.response

data class ExecutedTransactionParent(
    val receipt: ExecutedTransactionReceipt,
    val trx: ExecutedTransaction
)