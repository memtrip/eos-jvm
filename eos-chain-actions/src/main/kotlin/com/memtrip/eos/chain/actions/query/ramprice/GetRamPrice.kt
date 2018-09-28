package com.memtrip.eos.chain.actions.query.ramprice

import com.memtrip.eos.http.rpc.ChainApi
import com.memtrip.eos.http.rpc.model.contract.request.GetTableRows
import com.memtrip.eos.http.rpc.model.contract.response.ContractTableRows
import io.reactivex.Single
import java.math.BigDecimal

class GetRamPrice(
    private val chainApi: ChainApi
) {

    fun getPricePerKilobyte(): Single<Double> {
        return chainApi.getTableRows(GetTableRows(
            "eosio",
            "eosio",
            "rammarket",
            true,
            1,
            "",
            "",
            "",
            "",
            "dec"
        )).map { response ->
            if (response.isSuccessful) {
                val tableRows = response.body()!!.rows
                if (tableRows.isNotEmpty()) {
                    calculateRamPerKiloByte(response.body()!!)
                } else {
                    throw FailedToGetRamPrice()
                }
            } else {
                throw FailedToGetRamPrice()
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun calculateRamPerKiloByte(contractTableRows: ContractTableRows): Double {
        val row = contractTableRows.rows[0]
        val quote = row["quote"] as Map<String, String>
        val base = row["base"] as Map<String, String>

        val quoteBalance = value(quote["balance"]!!)
        val baseBalance = value(base["balance"]!!)
        val quoteWeight = value(quote["weight"]!!)

        return (quoteBalance.toDouble() / (baseBalance.toDouble()) * 1024)
    }

    private fun value(balance: String): BigDecimal {
        val split = balance.split(" ")[0]
        return BigDecimal(split)
    }

    class FailedToGetRamPrice : Exception()
}