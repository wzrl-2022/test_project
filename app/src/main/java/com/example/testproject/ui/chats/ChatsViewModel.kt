package com.example.testproject.ui.chats

import androidx.lifecycle.ViewModel
import com.example.testproject.helpers.usermanager.IUserManager
import com.example.testproject.repository.chats.Chat
import com.example.testproject.repository.chats.IChatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val userManager: IUserManager,
    private val chatsRepository: IChatsRepository
) : ViewModel() {
    private val _chatsFlow = MutableStateFlow(ChatsUiState.initial())
    val chatsFlow: StateFlow<ChatsUiState> = _chatsFlow

    fun onInitial() {
        _chatsFlow.update {
            val user = userManager.getUser() ?: throw IllegalStateException()
            val chats = chatsRepository.getChats()

            ChatsUiState.success(
                ChatsState(user, 0, chats)
            )
        }
    }

    fun onChatSelected(index: Int) {
        val state = _chatsFlow.value.state()
        _chatsFlow.update {
            ChatsUiState.success(state.copy(selected = index))
        }
    }

    fun onSendMessage(message: String) {
        val state = _chatsFlow.value.state()
        val newMessage = Chat.Message(
            author = Chat.Participant(state.profile.userName),
            message = message,
            date = Date(),
            byMe = true
        )
        chatsRepository.createMessage(state.chats[state.selected].title, newMessage)

        _chatsFlow.update {
            ChatsUiState.success(state.copy(chats = chatsRepository.getChats()))
        }
    }
}