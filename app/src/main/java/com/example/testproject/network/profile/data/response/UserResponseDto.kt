package com.example.testproject.network.profile.data.response

import com.example.testproject.helpers.moshi.DateJson
import com.example.testproject.helpers.usermanager.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class UserResponseDto(
    @Json(name = "id")
    val id: Long,
    @Json(name="name")
    val name: String,
    @Json(name = "username")
    val userName: String,
    @Json(name = "birthday")
    val birthday: String,
    @Json(name = "city")
    val city: String,
    @Json(name = "vk")
    val vk: String,
    @Json(name = "instagram")
    val instagram: String,
    @Json(name = "status")
    val status: String,
    @Json(name = "avatar")
    val avatar: String?,
    @Json(name = "last")
    @DateJson val last: Date?,
    @Json(name = "online")
    val online: Boolean,
    @Json(name = "created")
    @DateJson val created: Date,
    @Json(name = "phone")
    val phone: String,
    @Json(name = "completed_task")
    val completedTask: Int,
    @Json(name = "avatars")
    val avatars: AvatarsResponseDto?
) {
    @JsonClass(generateAdapter = true)
    data class AvatarsResponseDto(
        @Json(name = "avatar")
        val avatar: String,
        @Json(name = "bigAvatar")
        val bigAvatar: String,
        @Json(name = "miniAvatar")
        val miniAvatar: String
    )
}

fun UserResponseDto.asUser(): User =
    User(
        id = id,
        name = name,
        userName = userName,
        birthday = birthday,
        city = city,
        vk = vk,
        instagram = instagram,
        status = status,
        avatar = avatar,
        avatarEdited = false,
        last = last,
        online = online,
        created = created,
        phone = phone,
        completedTask = completedTask,
        avatars = avatars?.asAvatars()
    )

fun UserResponseDto.AvatarsResponseDto.asAvatars(): User.Avatars =
    User.Avatars(
        avatar = avatar,
        bigAvatar = bigAvatar,
        miniAvatar = miniAvatar
    )

