package com.example.testproject.di

import com.example.testproject.network.profile.ProfileService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class ProfileServiceModule {
    @Provides
    fun provideProfileService(@Authorized retrofit: Retrofit): ProfileService =
        retrofit.create(ProfileService::class.java)
}