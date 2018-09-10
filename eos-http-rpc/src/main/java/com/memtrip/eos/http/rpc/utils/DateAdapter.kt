package com.memtrip.eos.http.rpc.utils

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat

import java.util.Date

class DateAdapter {

    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

    @FromJson
    internal fun fromJson(timestamp: String): Date {
        return dateFormatter.parse(timestamp)
    }

    @ToJson
    internal fun toJson(date: Date): String {
        return dateFormatter.format(date)
    }
}