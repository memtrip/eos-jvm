package com.memtrip.eos.chain.actions.query.proxy

import com.memtrip.eos.abi.writer.bytewriter.NameWriter

import com.memtrip.eos.http.rpc.ChainApi
import com.memtrip.eos.http.rpc.model.contract.request.GetTableRows
import io.reactivex.Single

class GetRegProxyInfo(
    private val chainApi: ChainApi
) {

    fun getProxies(limit: Int, nextAccount: String = ""): Single<List<ProxyJson>> {
        return chainApi.getTableRows(GetTableRows(
            "regproxyinfo",
            "regproxyinfo",
            "proxies",
            true,
            limit,
            if (nextAccount.isEmpty()) { "" } else {
                (NameWriter().eosNameAsLong(nextAccount) + 1).toString()
            },
            "",
            "",
            "",
            "dec"
        )).map { response ->
            if (response.isSuccessful) {
                response.body()!!.rows.map { row ->
                    ProxyJson(
                        row["owner"].toString(),
                        row["name"].toString(),
                        row["website"].toString(),
                        row["slogan"].toString(),
                        row["philosophy"].toString(),
                        row["background"].toString(),
                        row["logo_256"].toString(),
                        row["telegram"].toString(),
                        row["steemit"].toString(),
                        row["twitter"].toString(),
                        row["wechat"].toString())
                }
            } else {
                throw FailedToFetchProxies()
            }
        }
    }

    class FailedToFetchProxies : Exception()
}