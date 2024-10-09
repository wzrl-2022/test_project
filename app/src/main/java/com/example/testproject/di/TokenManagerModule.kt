package com.example.testproject.di

import com.example.testproject.authorization.ITokenManager
import com.example.testproject.authorization.TokenManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface TokenManagerModule {
    @Binds
    @Singleton
    fun bindTokenManager(tokenManager: TokenManagerImpl): ITokenManager
}