package com.example.testproject.network.authorization.data.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SendAuthRequestDto(
    @Json(name = "phone")
    val phone: String
)