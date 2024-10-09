package com.example.testproject.network.authorization

import com.example.testproject.network.authorization.data.request.CheckAuthRequestDto
import com.example.testproject.network.authorization.data.request.RefreshTokenRequestDto
import com.example.testproject.network.authorization.data.request.RegisterRequestDto
import com.example.testproject.network.authorization.data.request.SendAuthRequestDto
import com.example.testproject.network.authorization.data.response.CheckAuthResponseDto
import com.example.testproject.network.authorization.data.response.RefreshTokenResponseDto
import com.example.testproject.network.authorization.data.response.RegisterResponseDto
import com.example.testproject.network.authorization.data.response.SendAuthResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthorizationService {
    @POST("users/send-auth-code/")
    suspend fun sendAuthCode(@Body sendAuthParam: SendAuthRequestDto) : SendAuthResponseDto

    @POST("users/check-auth-code/")
    suspend fun checkAuthCode(@Body checkAuthParam: CheckAuthRequestDto) : CheckAuthResponseDto

    @POST("users/register/")
    suspend fun register(@Body registerParam: RegisterRequestDto) : RegisterResponseDto

    @POST("users/refresh-token/")
    suspend fun refreshToken(@Body refreshTokenParam: RefreshTokenRequestDto) : RefreshTokenResponseDto
}