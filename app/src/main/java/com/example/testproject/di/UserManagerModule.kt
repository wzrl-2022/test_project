package com.example.testproject.di

import com.example.testproject.helpers.usermanager.IUserManager
import com.example.testproject.helpers.usermanager.UserManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UserManagerModule {
    @Binds
    @Singleton
    fun bindUserManager(userManager: UserManager): IUserManager
}