package com.example.testproject.ui.editprofile

data class EditProfileUiState(
    val isInitial: Boolean,
    val isLoading: Boolean,
    val isSuccess: Boolean,
    val isError: Boolean,
    private val state: EditProfileState?,
    private val error: Throwable?
) {
    fun state(): EditProfileState = state ?: throw IllegalStateException()

    fun error(): Throwable = error ?: throw IllegalStateException()

    companion object {
        fun initial() =
            EditProfileUiState(
                isInitial = true,
                isLoading = false,
                isSuccess = false,
                isError = false,
                state = null,
                error = null
            )

        fun loading() =
            EditProfileUiState(
                isInitial = false,
                isLoading = true,
                isSuccess = false,
                isError = false,
                state = null,
                error = null
            )

        fun success(state: EditProfileState) =
            EditProfileUiState(
                isInitial = false,
                isLoading = false,
                isSuccess = true,
                isError = false,
                state = state,
                error = null
            )

        fun error(error: Throwable) =
            EditProfileUiState(
                isInitial = false,
                isLoading = false,
                isSuccess = false,
                isError = true,
                state = null,
                error = error
            )
    }
}