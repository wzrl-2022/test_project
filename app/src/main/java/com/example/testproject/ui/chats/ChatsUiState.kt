package com.example.testproject.ui.chats

data class ChatsUiState(
    val isInitial: Boolean,
    val isLoading: Boolean,
    val isSuccess: Boolean,
    val isError: Boolean,
    private val state: ChatsState?,
    private val error: Throwable?
) {
    fun state(): ChatsState = state ?: throw IllegalStateException()

    fun error(): Throwable = error ?: throw IllegalStateException()

    companion object {
        fun initial() =
            ChatsUiState(
                isInitial = true,
                isLoading = false,
                isSuccess = false,
                isError = false,
                state = null,
                error = null
            )

        fun loading() =
            ChatsUiState(
                isInitial = false,
                isLoading = true,
                isSuccess = false,
                isError = false,
                state = null,
                error = null
            )

        fun success(state: ChatsState) =
            ChatsUiState(
                isInitial = false,
                isLoading = false,
                isSuccess = true,
                isError = false,
                state = state,
                error = null
            )

        fun error(error: Throwable) =
            ChatsUiState(
                isInitial = false,
                isLoading = false,
                isSuccess = false,
                isError = true,
                state = null,
                error = error
            )
    }
}