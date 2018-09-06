package com.memtrip.eos.http.rpc.utils

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat

import java.util.Date
import java.util.Locale

import javax.xml.bind.DatatypeConverter

class DateAdapter {

    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S", Locale.getDefault());

    @FromJson
    internal fun fromJson(timestamp: String): Date {
        return DatatypeConverter.parseDateTime(timestamp).time
    }

    @ToJson
    internal fun toJson(date: Date): String {
        return dateFormatter.format(date)
    }
}