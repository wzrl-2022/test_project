package com.example.testproject.network.authorization.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SendAuthResponseDto(
    @Json(name = "is_success")
    val isSuccess: Boolean
)