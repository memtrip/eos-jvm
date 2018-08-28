package com.memtrip.eos.http.rpc.model.history.response

data class HistoricAccountActionParent(
    val actions: List<HistoricAccountAction>,
    val last_irreversible_block: Int
)