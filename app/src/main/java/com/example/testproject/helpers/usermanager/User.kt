package com.example.testproject.helpers.usermanager

import com.example.testproject.db.UserEntity
import com.example.testproject.network.profile.data.request.UserRequestDto
import java.util.Date

data class User(
    val id: Long,
    val name: String,
    val userName: String,
    val birthday: String,
    val city: String,
    val vk: String,
    val instagram: String,
    val status: String,
    val avatar: String?,
    val avatarEdited: Boolean,
    val last: Date?,
    val online: Boolean,
    val created: Date,
    val phone: String,
    val completedTask: Int,
    val avatars: Avatars?
) {
    data class Avatars(
        val avatar: String,
        val bigAvatar: String,
        val miniAvatar: String
    )
}

fun User.asUserEntity(): UserEntity =
    UserEntity(
        id = id,
        name = name,
        userName = userName,
        birthday = birthday,
        city = city,
        vk = vk,
        instagram = instagram,
        status = status,
        avatar = avatar,
        last = last,
        online = online,
        created = created,
        phone = phone,
        completedTask = completedTask,
        avatars = avatars?.let {
            UserEntity.AvatarsEntity(
                avatarId = null,
                avatar = avatars.avatar,
                bigAvatar = avatars.bigAvatar,
                miniAvatar = avatars.miniAvatar
            )
        }
    )

fun User.asUserRequestDto(avatarRequest: UserRequestDto.AvatarRequestDto?): UserRequestDto =
    UserRequestDto(
        name = name,
        userName = userName,
        birthDay = birthday,
        city = city,
        vk = vk,
        instagram = instagram,
        status = status,
        avatar = avatarRequest
    )
