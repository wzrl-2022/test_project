package com.example.testproject.network.authorization.data.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterRequestDto(
    @Json(name = "phone")
    val phone: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "username")
    val userName: String
)