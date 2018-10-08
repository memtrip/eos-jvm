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

import com.memtrip.eos.chain.actions.query.producer.bpjson.response.BpNode
import com.memtrip.eos.chain.actions.query.producer.bpjson.response.BpParent

import com.memtrip.eos.http.rpc.ChainApi
import com.memtrip.eos.http.rpc.model.contract.request.GetTableRows
import com.memtrip.eos.http.rpc.model.producer.request.GetProducers
import com.squareup.moshi.Moshi

import io.reactivex.Single

class GetBlockProducersAggregate(
    private val chainApi: ChainApi,
    private val moshi: Moshi = Moshi.Builder().build()
) {

    fun getProducers(limit: Int): Single<List<BlockProducer>> {
        return getBpJson().flatMap { blockProducersContract ->
            chainApi.getProducers(GetProducers(
                true,
                "",
                limit
            )).map { response ->
                if (response.isSuccessful) {
                    val rows = response.body()!!.rows
                    rows.mapNotNull { producer ->
                        val bpJson = blockProducersContract.find { bpParent ->
                            producer.is_active == 1 && bpParent.producer_account_name == producer.owner
                        }

                        if (bpJson != null) {
                            val apiEndpoint = findApiEndPointInNodes(bpJson.nodes)
                            if (apiEndpoint != null) {
                                BlockProducer(
                                    producer,
                                    bpJson,
                                    apiEndpoint)
                            } else {
                                null
                            }
                        } else {
                            null
                        }
                    }
                } else {
                    throw FailedToFetchBlockProducer()
                }
            }
        }
    }

    private fun getBpJson(): Single<List<BpParent>> {
        return chainApi.getTableRows(GetTableRows(
            "producerjson",
            "producerjson",
            "producerjson",
            true,
            150,
            "",
            "",
            "",
            "",
            "dec"
        )).map { response ->
            if (response.isSuccessful) {
                response.body()!!.rows.mapNotNull { row ->
                    try {
                        moshi.adapter(BpParent::class.java).fromJson(row["json"] as String)
                    } catch (e: Exception) {
                        null
                    }
                }
            } else {
                throw FailedToFetchBlockProducer()
            }
        }
    }

    private fun findApiEndPointInNodes(nodes: List<BpNode>): String? {
        val sslEndPoint = nodes.find { node ->
            !node.ssl_endpoint.isNullOrEmpty()
        }

        return if (sslEndPoint != null) {
            sslEndPoint.ssl_endpoint
        } else {
            nodes.find { node ->
                !node.api_endpoint.isNullOrEmpty()
            }?.api_endpoint
        }
    }

    class FailedToFetchBlockProducer : Exception()
}