package com.example.testproject.ui.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.helpers.SimpleValidator
import com.example.testproject.network.authorization.AuthorizationService
import com.example.testproject.network.authorization.data.request.RegisterRequestDto
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException

@HiltViewModel(
    assistedFactory = RegistrationViewModel.Factory::class
)
class RegistrationViewModel @AssistedInject constructor(
    @Assisted private val phoneNumber: String,
    private val authorizationService: AuthorizationService
) : ViewModel() {
    private val validator = SimpleValidator()

    private val _registrationFlow = MutableStateFlow(RegistrationUiState.initial())
    val registrationFlow: StateFlow<RegistrationUiState> = _registrationFlow

    fun onInitial() {
        _registrationFlow.update {
            RegistrationUiState.success(
                RegistrationState(
                    phoneNumber,
                    name = "",
                    username = "",
                    validationError = false)
            )
        }
    }

    fun onNameChange(text: String) {
        val state = registrationFlow.value.state()
        _registrationFlow.update {
            RegistrationUiState.success(state.copy(name = text))
        }
    }

    fun onUsernameChange(text: String) {
        val state = registrationFlow.value.state()
        _registrationFlow.update {
            RegistrationUiState.success(state.copy(username = text, validationError = false))
        }
    }

    fun onRegister(onNavigate: () -> Unit) {
        val state = registrationFlow.value.state()
        if (validator.validate(state.username)) {
            viewModelScope.launch {
                _registrationFlow.update {
                    RegistrationUiState.loading()
                }

                try {
                    authorizationService.register(
                        RegisterRequestDto(
                            phoneNumber,
                            state.name,
                            state.username
                        )
                    )

                    onNavigate()
                } catch (e: Exception) {
                    _registrationFlow.update {
                        RegistrationUiState.error(Throwable(e.message))
                    }
                }
            }
        } else {
            _registrationFlow.update {
                RegistrationUiState.success(state.copy(validationError = true))
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(phoneNumber: String): RegistrationViewModel
    }
}