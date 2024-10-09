package com.example.testproject.network.profile

import com.example.testproject.network.authorization.data.response.AvatarsResponseDto
import com.example.testproject.network.profile.data.request.UserRequestDto
import com.example.testproject.network.profile.data.response.UserResponseDto
import com.serjltt.moshi.adapters.Wrapped
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface ProfileService {
    @GET("users/me/")
    @Wrapped(path = ["profile_data"])
    suspend fun me(): UserResponseDto

    @PUT("users/me/")
    suspend fun me(@Body user: UserRequestDto): AvatarsResponseDto
}