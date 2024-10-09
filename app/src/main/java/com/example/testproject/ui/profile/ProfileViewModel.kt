package com.example.testproject.ui.profile

import androidx.lifecycle.ViewModel
import com.example.testproject.helpers.usermanager.IUserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userManager: IUserManager
) : ViewModel() {
    private val _profileFlow = MutableStateFlow(ProfileUiState.initial())
    val profileFlow: StateFlow<ProfileUiState> = _profileFlow

    fun onInitial() {
        val user = userManager.getUser() ?: throw IllegalStateException()
        _profileFlow.update {
            ProfileUiState.success(
                ProfileState(user)
            )
        }
    }
}