package com.example.testproject.repository.chats

import java.util.Date

data class Chat(
    val title: String,
    val messages: MutableList<Message>,
    val participants: MutableList<Participant>
) {
    data class Participant(
        val nickname: String
    )

    data class Message(
        val author: Participant,
        val message: String,
        val date: Date,
        val byMe: Boolean
    )
}