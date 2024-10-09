package com.example.testproject.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.testproject.R
import com.example.testproject.helpers.parseDate
import com.example.testproject.helpers.resolveZodiac
import com.example.testproject.helpers.usermanager.User
import com.example.testproject.ui.common.TextLabel
import com.example.testproject.ui.error.ErrorContent
import com.example.testproject.ui.loading.LoadingContent
import com.example.testproject.ui.theme.Purple80
import com.example.testproject.ui.theme.Typography

@Composable
fun ProfileScreen(
    onOpenEditProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: ProfileViewModel = hiltViewModel()

    val uiState by viewModel.profileFlow.collectAsState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        if (uiState.isInitial) {
            viewModel.onInitial()
        } else if (uiState.isLoading) {
            LoadingContent()
        } else if (uiState.isSuccess) {
            ProfileContent(
                user = uiState.state().user,
                onEditProfile = onOpenEditProfile
            )
        } else if (uiState.isError) {
            ErrorContent(
                header = stringResource(id = R.string.error_title_default),
                message = uiState.error().message
                    ?: stringResource(id = R.string.error_message_default),
                actionText = "Ok",
                onAction = { viewModel.onInitial() }
            )
        }
    }
}

@Composable
fun ProfileContent(
    user: User,
    onEditProfile: () -> Unit,
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = "https://plannerok.ru/${user.avatars?.avatar}",
                    placeholder = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(Purple80)
                )
                Column(
                    modifier = Modifier
                        .height(72.dp)
                ) {
                    Text(
                        text = user.name,
                        style = Typography.bodyLarge,
                        modifier = Modifier
                            .weight(1f)
                            .padding(dimensionResource(R.dimen.padding_small))
                    )

                    Text(
                        text = "@${user.userName}",
                        style = Typography.bodyLarge,
                        modifier = Modifier
                            .weight(1f)
                            .padding(dimensionResource(R.dimen.padding_small))
                    )
                }
            }

            Spacer(
                modifier = Modifier
                    .size(dimensionResource(R.dimen.padding_medium))
            )

            TextLabel(
                text = user.phone,
                label = stringResource(R.string.label_phone),
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(
                modifier = Modifier
                    .size(dimensionResource(R.dimen.padding_medium))
            )

            TextLabel(
                text = user.city,
                label = stringResource(R.string.label_city),
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(
                modifier = Modifier
                    .size(dimensionResource(R.dimen.padding_medium))
            )

            TextLabel(
                text = user.birthday,
                label = stringResource(R.string.label_birthday),
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(
                modifier = Modifier
                    .size(dimensionResource(R.dimen.padding_medium))
            )

            TextLabel(
                text = resolveZodiac(user.birthday.parseDate("yyyy-MM-dd")),
                label = stringResource(R.string.label_birthday),
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Spacer(
            modifier = Modifier
                .size(dimensionResource(R.dimen.padding_big))
        )

        OutlinedButton(
            onClick = onEditProfile,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.label_edit_profile),
                style = Typography.labelSmall
            )
        }
    }
}