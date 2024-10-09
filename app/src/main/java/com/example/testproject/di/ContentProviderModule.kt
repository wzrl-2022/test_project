package com.example.testproject.di

import com.example.testproject.helpers.contentprovider.ContentProviderHelper
import com.example.testproject.helpers.contentprovider.IContentProviderHelper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ContentProviderModule {
    @Binds
    fun bindContentProviderHelper(contentProviderHelper: ContentProviderHelper) : IContentProviderHelper
}