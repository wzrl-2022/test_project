package com.example.testproject.network.profile.data.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserRequestDto(
    @Json(name = "name")
    val name: String,
    @Json(name = "username")
    val userName: String,
    @Json(name = "birthday")
    val birthDay: String,
    @Json(name = "city")
    val city: String,
    @Json(name = "vk")
    val vk: String,
    @Json(name = "instagram")
    val instagram: String,
    @Json(name = "status")
    val status: String,
    @Json(name = "avatar")
    val avatar: AvatarRequestDto?
) {
    @JsonClass(generateAdapter = true)
    data class AvatarRequestDto(
        @Json(name = "filename")
        val fileName: String,
        @Json(name = "base_64")
        val base64: String
    )
}