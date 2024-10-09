package com.example.testproject.ui.confirmphone

data class ConfirmPhoneUiState(
    val isInitial: Boolean,
    val isLoading: Boolean,
    val isSuccess: Boolean,
    val isError: Boolean,
    private val state: ConfirmPhoneState?,
    private val error: Throwable?
) {
    fun state(): ConfirmPhoneState = state ?: throw IllegalStateException()

    fun error(): Throwable = error ?: throw IllegalStateException()

    companion object {
        fun initial() =
            ConfirmPhoneUiState(
                isInitial = true,
                isLoading = false,
                isSuccess = false,
                isError = false,
                state = null,
                error = null
            )

        fun loading(state: ConfirmPhoneState) =
            ConfirmPhoneUiState(
                isInitial = false,
                isLoading = true,
                isSuccess = false,
                isError = false,
                state = state,
                error = null
            )

        fun success(state: ConfirmPhoneState) =
            ConfirmPhoneUiState(
                isInitial = false,
                isLoading = false,
                isSuccess = true,
                isError = false,
                state = state,
                error = null
            )

        fun error(error: Throwable) =
            ConfirmPhoneUiState(
                isInitial = false,
                isLoading = false,
                isSuccess = false,
                isError = true,
                state = null,
                error = error
            )
    }
}