package com.memtrip.eos.http.aggregation.producer

import com.memtrip.eos.http.aggregation.producer.bpjson.GetBpJson
import com.memtrip.eos.http.aggregation.producer.bpjson.response.BpParent

import com.memtrip.eos.http.rpc.ChainApi
import com.memtrip.eos.http.rpc.model.producer.request.GetProducers
import com.memtrip.eos.http.rpc.model.producer.response.Producer

import io.reactivex.Single

class GetBlockProducersAggregate(
    private val chainApi: ChainApi,
    private val getBpJson: GetBpJson = GetBpJson()
) {

    fun getProducers(limit: Int): Single<List<BlockProducer>> {
        return chainApi.getProducers(GetProducers(
            true,
            "",
            limit
        )).map { response ->
            if (response.isSuccessful) {
                response.body()!!.rows.map { producer ->
                    val bpParent = getBpJson(producer)
                    if (bpParent != null) {
                        BlockProducer(producer, bpParent)
                    } else {
                        null
                    }
                }.filterNotNull()
            } else {
                throw FailedToFetchBlockProducers()
            }
        }
    }

    private fun getBpJson(producer: Producer): BpParent? {
        val response = getBpJson.get(producer.url)

        return if (response.isSuccessful) {
            response.body
        } else {
            null
        }
    }

    class FailedToFetchBlockProducers : Exception()
}