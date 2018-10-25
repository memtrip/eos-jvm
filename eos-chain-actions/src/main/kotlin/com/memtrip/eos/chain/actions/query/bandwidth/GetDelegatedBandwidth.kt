package com.memtrip.eos.chain.actions.query.bandwidth

import com.memtrip.eos.http.rpc.ChainApi
import com.memtrip.eos.http.rpc.model.contract.request.GetTableRows
import io.reactivex.Single

class GetDelegatedBandwidth(private val chainApi: ChainApi) {

    fun getBandwidth(accountName: String): Single<List<BandwidthJson>> {
        return chainApi.getTableRows(GetTableRows(
            accountName,
            "eosio",
            "delband",
            "",
            true,
            100,
            "",
            "",
            "",
            "",
            "dec"
        )).map { response ->
            if (response.isSuccessful && response.body() != null) {
                val rows = response.body()!!.rows
                rows.map { row ->
                    BandwidthJson(
                        row["from"].toString(),
                        row["to"].toString(),
                        row["net_weight"].toString(),
                        row["cpu_weight"].toString())
                    }
            } else {
                throw FailedToFetchDelegatedBandwidth()
            }
        }
    }

    class FailedToFetchDelegatedBandwidth : Exception()
}