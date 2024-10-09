package com.example.testproject.ui.registration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testproject.R
import com.example.testproject.ui.common.EditTextLabel
import com.example.testproject.ui.common.TextLabel
import com.example.testproject.ui.error.ErrorContent
import com.example.testproject.ui.loading.LoadingContent
import com.example.testproject.ui.theme.Pink80
import com.example.testproject.ui.theme.Typography

@Composable
fun RegistrationScreen(
    phoneNumber: String,
    onChats: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = hiltViewModel<RegistrationViewModel, RegistrationViewModel.Factory>(
        creationCallback = { factory -> factory.create(phoneNumber) }
    )

    val uiState by viewModel.registrationFlow.collectAsState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        if (uiState.isInitial) {
            viewModel.onInitial()
        } else if (uiState.isLoading) {
            LoadingContent()
        } else if (uiState.isSuccess) {
            RegistrationContent(
                registrationState = uiState.state(),
                onNameChange = { text -> viewModel.onNameChange(text) },
                onUsernameChange = { text -> viewModel.onUsernameChange(text) },
                onRegister = { viewModel.onRegister(onChats) }
            )
        } else if (uiState.isError) {
            ErrorContent(
                header = stringResource(id = R.string.error_title_default),
                message = uiState.error().message ?: stringResource(id = R.string.error_message_default),
                actionText = "Ok",
                onAction = { viewModel.onInitial() }
            )
        }
    }
}

@Composable
fun RegistrationContent(
    registrationState: RegistrationState,
    onNameChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onRegister: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        TextLabel(
            text = registrationState.phoneNumber,
            label = stringResource(R.string.label_phone),
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(
            modifier = Modifier
                .size(dimensionResource(R.dimen.padding_medium))
        )

        EditTextLabel(
            text = registrationState.name,
            label = stringResource(R.string.label_name),
            onTextChanged = onNameChange,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(
            modifier = Modifier
                .size(dimensionResource(R.dimen.padding_medium))
        )

        EditTextLabel(
            text = registrationState.username,
            label = "@${stringResource(R.string.label_username)}",
            onTextChanged = onUsernameChange,
            highlight = registrationState.validationError,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
        )

        if (registrationState.validationError) {
            Spacer(
                modifier = Modifier
                    .size(dimensionResource(R.dimen.padding_small))
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Pink80,
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                Text(
                    text = stringResource(R.string.username_validation_error),
                    textAlign = TextAlign.Center,
                    style = Typography.bodyLarge,
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_medium))
                        .fillMaxWidth()
                )
            }
        }

        Spacer(
            modifier = Modifier
                .size(dimensionResource(R.dimen.padding_big))
        )

        OutlinedButton(
            onClick = onRegister,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.label_register),
                style = Typography.labelSmall
            )
        }
    }
}