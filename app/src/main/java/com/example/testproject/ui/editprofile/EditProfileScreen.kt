package com.example.testproject.ui.editprofile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.testproject.R
import com.example.testproject.helpers.usermanager.User
import com.example.testproject.ui.common.EditTextLabel
import com.example.testproject.ui.common.TextLabel
import com.example.testproject.ui.error.ErrorContent
import com.example.testproject.ui.loading.LoadingContent
import com.example.testproject.ui.theme.SemiPurple40
import com.example.testproject.ui.theme.Typography

@Composable
fun EditProfileScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: EditProfileViewModel = hiltViewModel()

    val uiState by viewModel.editProfileFlow.collectAsState()

    val pickImage = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        uri?.also { viewModel.onAvatarChanged(it.toString()) }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        if (uiState.isInitial) {
            viewModel.onInitial()
        } else if (uiState.isLoading) {
            LoadingContent()
        } else if (uiState.isSuccess) {
            EditProfileContent(
                user = uiState.state().user,
                onEditAvatar = { pickImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                onPhoneChanged = { phone -> viewModel.onPhoneChanged(phone) },
                onCityChanged = { city -> viewModel.onCityChanged(city) },
                onBirthdayChanged = { birthday -> viewModel.onBirthdayChanged(birthday) },
                onSaveProfile = { viewModel.onSaveUser() }
            )
        } else if (uiState.isError) {
            ErrorContent(
                header = stringResource(id = R.string.error_title_default),
                message = uiState.error().message
                    ?: stringResource(id = R.string.error_message_default),
                "Ok",
                { viewModel.onInitial() }
            )
        }
    }
}

@Composable
fun EditProfileContent(
    user: User,
    onEditAvatar: () -> Unit,
    onPhoneChanged: (String) -> Unit,
    onCityChanged: (String) -> Unit,
    onBirthdayChanged: (String) -> Unit,
    onSaveProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(160.dp)
                    .clip(CircleShape)
            ) {
                AsyncImage(
                    model = "https://plannerok.ru/${user.avatars?.avatar}",
                    placeholder = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(160.dp)
                )

                Text(
                    text = stringResource(R.string.label_edit),
                    style = Typography.titleLarge,
                    color = Color.LightGray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .align(Alignment.BottomCenter)
                        .background(SemiPurple40)
                        .clickable { onEditAvatar() }
                )
            }

            Spacer(
                modifier = Modifier
                    .size(dimensionResource(R.dimen.padding_medium))
            )

            TextLabel(
                text = user.name,
                label = stringResource(R.string.label_name),
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(
                modifier = Modifier
                    .size(dimensionResource(R.dimen.padding_medium))
            )

            TextLabel(
                text = "@${user.userName}",
                label = stringResource(R.string.label_username),
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(
                modifier = Modifier
                    .size(dimensionResource(R.dimen.padding_big))
            )

            EditTextLabel(
                text = user.phone,
                onTextChanged = onPhoneChanged,
                label = stringResource(R.string.label_phone),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(
                modifier = Modifier
                    .size(dimensionResource(R.dimen.padding_big))
            )

            EditTextLabel(
                text = user.city,
                onTextChanged = onCityChanged,
                label = stringResource(R.string.label_city),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(
                modifier = Modifier
                    .size(dimensionResource(R.dimen.padding_big))
            )

            EditTextLabel(
                text = user.birthday,
                onTextChanged = onBirthdayChanged,
                label = stringResource(R.string.label_birthday),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Spacer(
            modifier = Modifier
                .size(dimensionResource(R.dimen.padding_big))
        )

        OutlinedButton(
            onClick = onSaveProfile,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.label_save_profile),
                style = Typography.labelSmall
            )
        }
    }
}