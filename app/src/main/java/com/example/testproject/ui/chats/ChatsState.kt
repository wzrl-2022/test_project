package com.example.testproject.ui.chats

import com.example.testproject.helpers.usermanager.User
import com.example.testproject.repository.chats.Chat

data class ChatsState(
    val profile: User,
    val selected: Int,
    val chats: List<Chat>
)