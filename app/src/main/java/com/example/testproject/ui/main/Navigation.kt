package com.example.testproject.ui.main

import kotlinx.serialization.Serializable

@Serializable
object Splash

@Serializable
object SignIn

@Serializable
data class ConfirmPhone(
    val phoneNumber: String
)

@Serializable
data class Registration(
    val phoneNumber: String
)

@Serializable
object Chats

@Serializable
object Profile

@Serializable
object EditProfile

enum class StartDestination {
    INITIAL,
    NOT_LOGGED,
    LOGGED
}