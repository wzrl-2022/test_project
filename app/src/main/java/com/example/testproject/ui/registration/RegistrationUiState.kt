package com.example.testproject.ui.registration

data class RegistrationUiState(
    val isInitial: Boolean,
    val isLoading: Boolean,
    val isSuccess: Boolean,
    val isError: Boolean,
    private val state: RegistrationState?,
    private val error: Throwable?
) {
    fun state(): RegistrationState = state ?: throw IllegalStateException()

    fun error(): Throwable = error ?: throw IllegalStateException()

    companion object {
        fun initial() =
            RegistrationUiState(
                isInitial = true,
                isLoading = false,
                isSuccess = false,
                isError = false,
                state = null,
                error = null
            )

        fun loading() =
            RegistrationUiState(
                isInitial = false,
                isLoading = true,
                isSuccess = false,
                isError = false,
                state = null,
                error = null
            )

        fun success(state: RegistrationState) =
            RegistrationUiState(
                isInitial = false,
                isLoading = false,
                isSuccess = true,
                isError = false,
                state = state,
                error = null
            )

        fun error(error: Throwable) =
            RegistrationUiState(
                isInitial = false,
                isLoading = false,
                isSuccess = false,
                isError = true,
                state = null,
                error = error
            )
    }
}