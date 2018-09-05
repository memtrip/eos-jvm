package com.memtrip.eos.http.aggregation.producer

import com.memtrip.eos.http.aggregation.producer.bpjson.response.BpParent
import com.memtrip.eos.http.rpc.model.producer.response.Producer

data class BlockProducer(
    val producer: Producer,
    val bpJson: BpParent
)