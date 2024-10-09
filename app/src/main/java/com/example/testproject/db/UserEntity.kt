package com.example.testproject.db

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.testproject.helpers.usermanager.User
import java.util.Date

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "username") val userName: String,
    @ColumnInfo(name = "birthday") val birthday: String,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "vk") val vk: String,
    @ColumnInfo(name = "instagram") val instagram: String,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "avatar") val avatar: String?,
    @ColumnInfo(name = "last") val last: Date?,
    @ColumnInfo(name = "online") val online: Boolean,
    @ColumnInfo(name = "created") val created: Date,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "completed_task") val completedTask: Int,
    @Embedded val avatars: AvatarsEntity?
) {
    @Entity(tableName = "avatar")
    data class AvatarsEntity(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "avatar_id") val avatarId: Long?,
        @ColumnInfo(name = "normal_avatar") val avatar: String,
        @ColumnInfo(name = "big_avatar") val bigAvatar: String,
        @ColumnInfo(name = "mini_avatar") val miniAvatar: String
    )
}

fun UserEntity.asUser(): User =
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
        avatars = avatars?.let {
            User.Avatars(
                avatar = avatars.avatar,
                bigAvatar = avatars.bigAvatar,
                miniAvatar = avatars.miniAvatar
            )
        }
    )