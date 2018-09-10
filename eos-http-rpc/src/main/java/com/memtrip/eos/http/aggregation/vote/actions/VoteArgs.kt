package com.memtrip.eos.http.aggregation.vote.actions

import com.memtrip.eos.abi.writer.Abi
import com.memtrip.eos.abi.writer.AccountNameCollectionCompress

import com.memtrip.eos.abi.writer.AccountNameCompress

@Abi
data class VoteArgs(
    val voter: String,
    val proxy: String,
    val producers: List<String>
) {

    val getVoter: String
        @AccountNameCompress get() = voter

    val getProxy: String
        @AccountNameCompress get() = proxy

    val getProducers: List<String>
        @AccountNameCollectionCompress get() = producers
}