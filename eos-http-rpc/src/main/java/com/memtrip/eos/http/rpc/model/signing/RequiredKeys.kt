package com.memtrip.eos.http.rpc.model.signing

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RequiredKeys(
    val required_keys: List<String>
)