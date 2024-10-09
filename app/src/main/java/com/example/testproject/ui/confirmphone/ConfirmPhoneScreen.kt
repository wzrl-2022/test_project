package com.example.testproject.ui.confirmphone

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testproject.R
import com.example.testproject.ui.error.ErrorContent
import com.example.testproject.ui.loading.LoadingContent
import com.example.testproject.ui.theme.Purple80
import com.example.testproject.ui.theme.PurpleGrey40
import com.example.testproject.ui.theme.Typography

@Composable
fun ConfirmPhoneScreen(
    phoneNumber: String,
    onNavigateRegistration: (String) -> Unit,
    onNavigateChats: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = hiltViewModel<ConfirmPhoneViewModel, ConfirmPhoneViewModel.Factory>(
        creationCallback = { factory -> factory.create(phoneNumber) }
    )

    val uiState by viewModel.confirmPhoneFlow.collectAsState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        if (uiState.isInitial) {
            viewModel.onInitial()
        } else if (uiState.isError) {
            ErrorContent(
                header = stringResource(id = R.string.error_title_default),
                message = uiState.error().message ?: stringResource(id = R.string.error_message_default),
                actionText = "Ok",
                onAction = { viewModel.onInitial() }
            )
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ConfirmPhoneContent(
                    code = uiState.state().code,
                    isLoading = uiState.isLoading,
                    onCodeChanged = { code -> viewModel.onCodeInput(code, onNavigateRegistration, onNavigateChats) }
                )
            }
        }
    }
}

@Composable
fun ConfirmPhoneContent(
    code: String,
    isLoading: Boolean,
    onCodeChanged: (String) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.confirm_phone_number),
                style = Typography.bodyLarge,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_big))
            )

            Spacer(
                modifier = Modifier
                    .size(size = dimensionResource(id = R.dimen.padding_big))
            )

            BasicTextField(
                value = TextFieldValue(
                    text = code,
                    selection = TextRange(code.length)
                ),
                onValueChange = { value ->
                    if (value.text.length <= 6) {
                        onCodeChanged(value.text)
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword
                ),
                decorationBox = {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(6) { index ->
                            val char = when {
                                index >= code.length -> ""
                                else -> code[index].toString()
                            }

                            CharInput(
                                char = char,
                                isFocused = index == code.length
                            )

                            Spacer(
                                modifier = Modifier.size(
                                    size = dimensionResource(id = R.dimen.padding_small)
                                )
                            )
                        }
                    }
                }
            )

            if (isLoading) {
                Spacer(
                    modifier = Modifier
                        .size(size = dimensionResource(id = R.dimen.padding_medium))
                )
                LoadingContent()
            }
        }
    }
}

@Composable
fun CharInput(
    char: String,
    isFocused: Boolean
) {
    Text(
        text = char,
        style = Typography.titleLarge,
        textAlign = TextAlign.Center,
        maxLines = 1,
        modifier = Modifier
            .size(48.dp)
            .wrapContentHeight()
            .border(
                width = 1.dp,
                color = if (isFocused) PurpleGrey40 else Purple80,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(4.dp)
    )
}