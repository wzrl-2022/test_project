package com.example.testproject.ui.signin

data class SignInUiState(
    val isInitial: Boolean,
    val isLoading: Boolean,
    val isSuccess: Boolean,
    val isError: Boolean,
    private val state: SignInState?,
    private val error: Throwable?
) {
    fun state(): SignInState = state ?: throw IllegalStateException()

    fun error(): Throwable = error ?: throw IllegalStateException()

    companion object {
        fun initial() =
            SignInUiState(
                isInitial = true,
                isLoading = false,
                isSuccess = false,
                isError = false,
                state = null,
                error = null,
            )

        fun loading() =
            SignInUiState(
                isInitial = false,
                isLoading = true,
                isSuccess = false,
                isError = false,
                state = null,
                error = null
            )

        fun success(state: SignInState) =
            SignInUiState(
                isInitial = false,
                isLoading = false,
                isSuccess = true,
                isError = false,
                state = state,
                error = null
            )

        fun error(error: Throwable) =
            SignInUiState(
                isInitial = false,
                isLoading = false,
                isSuccess = false,
                isError = true,
                state = null,
                error = error
            )
    }
}