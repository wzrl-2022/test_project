package com.example.testproject.di

import com.example.testproject.network.authorization.AuthorizationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class AuthorizationModule {
    @Provides
    fun provideAuthorizationService(@Unauthorized retrofit: Retrofit): AuthorizationService =
        retrofit.create(AuthorizationService::class.java)
}