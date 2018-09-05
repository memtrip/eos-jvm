package com.memtrip.eos.http.rpc.utils

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.threeten.bp.LocalDateTime

class LocalDateTimeAdapter {

    @FromJson
    internal fun fromJson(timestamp: String): LocalDateTime {
        return LocalDateTime.parse(timestamp)
    }

    @ToJson
    internal fun toJson(localDateTime: LocalDateTime): String {
        return localDateTime.toString()
    }
}