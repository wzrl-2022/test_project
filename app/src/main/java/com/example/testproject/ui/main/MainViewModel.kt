package com.example.testproject.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.helpers.usermanager.IUserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userManager: IUserManager
) : ViewModel() {
    private val _isLoggedFlow = MutableStateFlow(StartDestination.INITIAL)
    val isLoggedFlow: StateFlow<StartDestination> = _isLoggedFlow

    fun readCurrentUser() {
        viewModelScope.launch {
            userManager.readUser()
            _isLoggedFlow.update {
                if (userManager.getUser() != null)
                    StartDestination.LOGGED
                else
                    StartDestination.NOT_LOGGED
            }
        }
    }

    fun logoutUser() {
        viewModelScope.launch {
            userManager.getUser()?.also { user ->
                userManager.deleteUser(user)
            }
            _isLoggedFlow.update { StartDestination.NOT_LOGGED }
        }
    }
}