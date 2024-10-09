package com.example.testproject.ui.editprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.helpers.asBase64
import com.example.testproject.helpers.contentprovider.IContentProviderHelper
import com.example.testproject.helpers.usermanager.IUserManager
import com.example.testproject.helpers.usermanager.asUserRequestDto
import com.example.testproject.network.profile.ProfileService
import com.example.testproject.network.profile.data.request.UserRequestDto
import com.example.testproject.network.profile.data.response.asUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userManager: IUserManager,
    private val profileService: ProfileService,
    private val contentProviderHelper: IContentProviderHelper
) : ViewModel() {
    private val _editProfileFlow = MutableStateFlow(EditProfileUiState.initial())
    val editProfileFlow: StateFlow<EditProfileUiState> = _editProfileFlow

    fun onInitial() {
        val user = userManager.getUser() ?: throw IllegalStateException()
        _editProfileFlow.update {
            EditProfileUiState.success(
                EditProfileState(user)
            )
        }
    }

    fun onAvatarChanged(uri: String) {
        val state = _editProfileFlow.value.state()
        _editProfileFlow.update {
            EditProfileUiState.success(
                EditProfileState(state.user.copy(avatar = uri, avatarEdited = true))
            )
        }
    }

    fun onPhoneChanged(phone: String) {
        val state = _editProfileFlow.value.state()
        _editProfileFlow.update {
            EditProfileUiState.success(
                EditProfileState(state.user.copy(phone = phone))
            )
        }
    }

    fun onCityChanged(city: String) {
        val state = _editProfileFlow.value.state()
        _editProfileFlow.update {
            EditProfileUiState.success(
                EditProfileState(state.user.copy(city = city))
            )
        }
    }

    fun onBirthdayChanged(birthday: String) {
        val state = _editProfileFlow.value.state()
        _editProfileFlow.update {
            EditProfileUiState.success(
                EditProfileState(state.user.copy(birthday = birthday))
            )
        }
    }

    fun onSaveUser() {
        viewModelScope.launch {
            val user = _editProfileFlow.value.state().user

            _editProfileFlow.update {
                EditProfileUiState.loading()
            }

            try {
                val avatarRequestDto = if (user.avatarEdited) {
                    val avatar = user.avatar ?: throw IllegalStateException()
                    val fileName = contentProviderHelper.getFileName(avatar)
                    val fileContent = contentProviderHelper.getFileContent(avatar).asBase64()

                    UserRequestDto.AvatarRequestDto(fileName, fileContent)
                } else {
                    UserRequestDto.AvatarRequestDto("", "")
                }
                profileService.me(user.asUserRequestDto(avatarRequestDto))

                val updatedUser = profileService.me().asUser()

                userManager.updateUser(updatedUser)

                _editProfileFlow.update {
                    EditProfileUiState.success(
                        EditProfileState(userManager.getUser() ?: throw IllegalStateException())
                    )
                }
            } catch (e: Exception) {
                _editProfileFlow.update {
                    EditProfileUiState.error(Throwable(e.message))
                }
            }
        }
    }
}