package com.example.testproject.helpers

class RefreshTokenAction private constructor(
    private val onRefreshTokenFail: () -> Unit
) {
    fun onFail() = onRefreshTokenFail()

    companion object {
        private var INSTANCE: RefreshTokenAction? = null

        fun create(onRefreshTokenFail: () -> Unit) {
            INSTANCE = RefreshTokenAction(onRefreshTokenFail)
        }

        fun getInstance(): RefreshTokenAction {
            return INSTANCE ?: throw IllegalStateException()
        }
    }
}