package com.example.testproject.helpers.moshi

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Retention
@JsonQualifier
annotation class DateJson

class DateAdapter {
    @FromJson
    @DateJson
    fun fromJson(value: String): Date {
        try {
            return SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ssXXX", Locale.getDefault()).parse(value)
                ?: throw IllegalArgumentException("Can't convert value of $value to Date")
        } catch (e: ParseException) {
            throw IllegalArgumentException("Can't convert value of $value to Date")
        }
    }

    @ToJson
    fun toJson(@DateJson value: Date): String {
        return SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ssXXX", Locale.getDefault()).format(value)
    }
}