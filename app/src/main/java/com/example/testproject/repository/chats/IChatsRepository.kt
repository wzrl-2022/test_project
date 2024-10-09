package com.example.testproject.repository.chats

interface IChatsRepository {
    fun getChats(): List<Chat>
    fun createMessage(chatTitle: String, message: Chat.Message)
}