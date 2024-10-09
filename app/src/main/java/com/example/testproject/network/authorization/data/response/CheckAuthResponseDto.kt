package com.example.testproject.network.authorization.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CheckAuthResponseDto(
    @Json(name = "refresh_token")
    val refreshToken: String,
    @Json(name = "access_token")
    val accessToken: String,
    @Json(name = "user_id")
    val userId: Long,
    @Json(name = "is_user_exists")
    val isUserExists: Boolean
)