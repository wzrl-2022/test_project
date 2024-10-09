package com.example.testproject.helpers

import android.util.Base64
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.format(format: String): String =
    SimpleDateFormat(format, Locale.getDefault()).format(this)

fun String.parseDate(format: String): Date =
    SimpleDateFormat(format, Locale.getDefault()).parse(this)
        ?: throw IllegalArgumentException("String value of $this does not match the pattern of $format")

fun ByteArray.asBase64(): String =
    Base64.encodeToString(this, Base64.DEFAULT)