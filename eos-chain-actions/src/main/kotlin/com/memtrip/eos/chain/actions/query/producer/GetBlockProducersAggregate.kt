/**
 * Copyright 2013-present memtrip LTD.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.memtrip.eos.chain.actions.query.producer

import com.memtrip.eos.chain.actions.query.producer.bpjson.GetBpJson
import com.memtrip.eos.chain.actions.query.producer.bpjson.response.BpParent

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
                        if (bpParent.nodes.isNotEmpty() &&
                            bpParent.nodes[0].api_endpoint != null) {
                            BlockProducer(producer, bpParent, bpParent.nodes[0].api_endpoint!!)
                        } else {
                            null
                        }
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