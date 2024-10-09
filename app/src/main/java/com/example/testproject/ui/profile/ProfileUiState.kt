package com.example.testproject.ui.profile

data class ProfileUiState(
    val isInitial: Boolean,
    val isLoading: Boolean,
    val isSuccess: Boolean,
    val isError: Boolean,
    private val state: ProfileState?,
    private val error: Throwable?
) {
    fun state(): ProfileState = state ?: throw IllegalStateException()

    fun error(): Throwable = error ?: throw IllegalStateException()

    companion object {
        fun initial() =
            ProfileUiState(
                isInitial = true,
                isLoading = false,
                isSuccess = false,
                isError = false,
                state = null,
                error = null
            )

        fun loading() =
            ProfileUiState(
                isInitial = false,
                isLoading = true,
                isSuccess = false,
                isError = false,
                state = null,
                error = null
            )

        fun success(state: ProfileState) =
            ProfileUiState(
                isInitial = false,
                isLoading = false,
                isSuccess = true,
                isError = false,
                state = state,
                error = null
            )

        fun error(error: Throwable) =
            ProfileUiState(
                isInitial = false,
                isLoading = false,
                isSuccess = false,
                isError = true,
                state = null,
                error = error
            )
    }
}