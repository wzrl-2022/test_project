package com.example.testproject.authorization

import android.content.Context
import android.content.Context.MODE_PRIVATE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val PREFS = "authorization"

private const val PREFS_ACCESS_TOKEN_KEY = "authorization:access_token"
private const val PREFS_REFRESH_TOKEN_KEY = "authorization:refresh_token"

class TokenManagerImpl @Inject constructor(
    @ApplicationContext val context: Context
) : ITokenManager {
    private var tokens: Tokens = Tokens("", "")

    override fun getTokens(): Tokens {
        if (!tokens.isEmpty()) {
            return tokens
        }

        val accessToken = context.getSharedPreferences(PREFS, MODE_PRIVATE)
            .getString(PREFS_ACCESS_TOKEN_KEY, "")
        val refreshToken = context.getSharedPreferences(PREFS, MODE_PRIVATE)
            .getString(PREFS_REFRESH_TOKEN_KEY, "")

        tokens = Tokens(accessToken, refreshToken)

        return tokens
    }


    override fun saveTokens(accessToken: String, refreshToken: String) {
        context.getSharedPreferences(PREFS, MODE_PRIVATE)
            .edit()
            .also { editor ->
                editor.putString(PREFS_ACCESS_TOKEN_KEY, accessToken)
                editor.putString(PREFS_REFRESH_TOKEN_KEY, refreshToken)
                editor.commit()
            }
    }
}