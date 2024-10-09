package com.example.testproject.network.authorization.data.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CheckAuthRequestDto(
    @Json(name = "phone")
    val phone: String,
    @Json(name = "code")
    val code: String
)