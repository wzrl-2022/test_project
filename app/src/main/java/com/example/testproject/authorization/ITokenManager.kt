package com.example.testproject.authorization

interface ITokenManager {
    fun getTokens(): Tokens
    fun saveTokens(accessToken: String, refreshToken: String)
}