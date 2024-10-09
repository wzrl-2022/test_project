package com.example.testproject.network.authorization.data.response

import com.example.testproject.network.profile.data.response.UserResponseDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AvatarsResponseDto(
    @Json(name = "avatars")
    val avatars: UserResponseDto.AvatarsResponseDto?
)