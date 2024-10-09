package com.example.testproject.ui.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {
    private val _signInFlow = MutableStateFlow(SignInUiState.initial())
    val signInFlow: StateFlow<SignInUiState> = _signInFlow

    fun onInitial() {
        viewModelScope.launch {
            _signInFlow.update { SignInUiState.success(SignInState("")) }
        }
    }

    fun onPhoneNumberChanged(phoneNumber: String) {
        _signInFlow.update { SignInUiState.success(SignInState(phoneNumber)) }
    }

    fun onSendPhone(onNavigate: (String) -> Unit) {
        val state = _signInFlow.value.state()

        onNavigate(state.phoneNumber)
    }
}