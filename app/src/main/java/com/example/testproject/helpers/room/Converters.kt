package com.example.testproject.helpers.room

import androidx.room.TypeConverter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Converters {
    @TypeConverter
    fun stringToDate(value: String): Date {
        try {
            return SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ssXXX", Locale.getDefault()).parse(value)
                ?: throw IllegalArgumentException("Can't convert value of $value to Date")
        } catch (e: ParseException) {
            throw IllegalArgumentException("Can't convert value of $value to Date")
        }
    }

    @TypeConverter
    fun dateToString(value: Date): String {
        return SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ssXXX", Locale.getDefault()).format(value)
    }
}