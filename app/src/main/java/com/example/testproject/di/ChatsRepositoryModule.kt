package com.example.testproject.di

import com.example.testproject.repository.chats.ChatsRepository
import com.example.testproject.repository.chats.IChatsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface ChatsRepositoryModule {
    @Binds
    fun bindChatRepository(chatsRepository: ChatsRepository): IChatsRepository
}