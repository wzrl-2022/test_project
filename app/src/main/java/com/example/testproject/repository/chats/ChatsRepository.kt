package com.example.testproject.repository.chats

import java.util.Date
import javax.inject.Inject

class ChatsRepository @Inject constructor() : IChatsRepository {
    private val _chats = createChats()

    override fun getChats(): List<Chat> = _chats

    override fun createMessage(chatTitle: String, message: Chat.Message) {
        for (chat in _chats) {
            if (chat.title == chatTitle) {
                chat.messages.add(message)

                return
            }
        }
    }

    private fun createChats() : List<Chat> {
        val me = "Shepard"

        val joel = Chat.Participant("Joel")
        val ellie = Chat.Participant("Ellie")

        val alyx = Chat.Participant("Alyx")
        val gordon = Chat.Participant("Gordon")

        val shepard = Chat.Participant("Shepard")
        val tali = Chat.Participant("Tali")

        val tlouChat = Chat(
            "The Last Of Us",
            mutableListOf(
                Chat.Message(
                    joel,
                    "Hi",
                    Date(),
                    me == joel.nickname
                ),
                Chat.Message(
                    ellie,
                    "When we start?",
                    Date(),
                    me == ellie.nickname
                )
            ),
            mutableListOf(
                joel,
                ellie
            )
        )

        val halfLifeChat = Chat(
            title = "Half-Life",
            mutableListOf(
                Chat.Message(
                    gordon,
                    "Hi",
                    Date(),
                    me == gordon.nickname
                ),
                Chat.Message(
                    alyx,
                    "When we start?",
                    Date(),
                    me == alyx.nickname
                )
            ),
            mutableListOf(
                alyx,
                gordon
            )
        )

        val massEffectChat = Chat(
            title = "Mass Effect",
            mutableListOf(
                Chat.Message(
                    shepard,
                    "Hi",
                    Date(),
                    me == shepard.nickname
                ),
                Chat.Message(
                    tali,
                    "When we start?",
                    Date(),
                    me == tali.nickname
                ),
                Chat.Message(
                    shepard,
                    "I should go.",
                    Date(),
                    me == shepard.nickname
                )
            ),
            mutableListOf(
                shepard,
                tali
            )
        )

        return mutableListOf(tlouChat, halfLifeChat, massEffectChat)
    }
}