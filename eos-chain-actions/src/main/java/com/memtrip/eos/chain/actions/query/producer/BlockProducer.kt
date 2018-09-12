package com.memtrip.eos.chain.actions.query.producer

import com.memtrip.eos.chain.actions.query.producer.bpjson.response.BpParent
import com.memtrip.eos.http.rpc.model.producer.response.Producer

data class BlockProducer(
    val producer: Producer,
    val bpJson: BpParent,
    val apiEndpoint: String
)