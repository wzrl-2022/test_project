package com.example.testproject.ui.confirmphone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.authorization.ITokenManager
import com.example.testproject.helpers.usermanager.IUserManager
import com.example.testproject.network.authorization.AuthorizationService
import com.example.testproject.network.authorization.data.request.CheckAuthRequestDto
import com.example.testproject.network.authorization.data.request.SendAuthRequestDto
import com.example.testproject.network.profile.ProfileService
import com.example.testproject.network.profile.data.response.asUser
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(
    assistedFactory = ConfirmPhoneViewModel.Factory::class
)
class ConfirmPhoneViewModel @AssistedInject constructor(
    @Assisted private val phoneNumber: String,
    private val authorizationService: AuthorizationService,
    private val profileService: ProfileService,
    private val userManager: IUserManager,
    private val tokenManager: ITokenManager
) : ViewModel() {
    private val _confirmPhoneFlow = MutableStateFlow(ConfirmPhoneUiState.initial())
    val confirmPhoneFlow: StateFlow<ConfirmPhoneUiState> = _confirmPhoneFlow

    fun onInitial() {
        viewModelScope.launch {
            _confirmPhoneFlow.update {
                ConfirmPhoneUiState.loading(ConfirmPhoneState(phoneNumber, ""))
            }

            try {
                val response = authorizationService.sendAuthCode(SendAuthRequestDto(phoneNumber))
                if (response.isSuccess) {
                    _confirmPhoneFlow.update {
                        ConfirmPhoneUiState.success(
                            ConfirmPhoneState(phoneNumber, "")
                        )
                    }
                } else {
                    _confirmPhoneFlow.update {
                        ConfirmPhoneUiState.error(Throwable("Error during send phone number."))
                    }
                }
            } catch (e: Exception) {
                _confirmPhoneFlow.update {
                    ConfirmPhoneUiState.error(Throwable(e.message))
                }
            }
        }
    }

    fun onCodeInput(code: String, onNavigateRegistration: (String) -> Unit, onNavigateChats: () -> Unit) {
        _confirmPhoneFlow.update {
            ConfirmPhoneUiState.success(
                ConfirmPhoneState(phoneNumber, code)
            )
        }

        val state = _confirmPhoneFlow.value.state()
        if (code.length >= 6) {
            viewModelScope.launch {
                _confirmPhoneFlow.update {
                    ConfirmPhoneUiState.loading(state)
                }

                try {
                    val response = authorizationService.checkAuthCode(CheckAuthRequestDto(phoneNumber, code))

                    tokenManager.saveTokens(response.accessToken, response.refreshToken)

                    if (response.isUserExists) {
                        val user = profileService.me().asUser()
                        userManager.saveUser(user)

                        onNavigateChats()
                    } else {
                        onNavigateRegistration(phoneNumber)
                    }
                } catch (e: Exception) {
                    _confirmPhoneFlow.update {
                        ConfirmPhoneUiState.error(Throwable(e.message))
                    }
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(phoneNumber: String): ConfirmPhoneViewModel
    }
}