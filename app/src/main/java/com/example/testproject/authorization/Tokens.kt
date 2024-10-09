package com.example.testproject.authorization

data class Tokens(
    val accessToken: String?,
    val refreshToken: String?
) {
    fun isEmpty() = accessToken.isNullOrEmpty() && refreshToken.isNullOrEmpty()
}