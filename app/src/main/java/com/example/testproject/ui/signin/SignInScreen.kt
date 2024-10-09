package com.example.testproject.ui.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testproject.R
import com.example.testproject.ui.error.ErrorContent
import com.example.testproject.ui.loading.LoadingContent
import com.example.testproject.ui.theme.Typography
import com.example.testproject.ui.common.phonenumbertextfield.component.MaterialCountryCodePicker
import com.example.testproject.ui.common.phonenumbertextfield.data.ccpDefaultColors
import com.example.testproject.ui.common.phonenumbertextfield.data.utils.getDefaultLangCode
import com.example.testproject.ui.common.phonenumbertextfield.data.utils.getLibCountries

@Composable
fun SignInScreen(
    onOpenConfirmPhone: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: SignInViewModel = hiltViewModel()

    val uiState by viewModel.signInFlow.collectAsState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        if (uiState.isInitial) {
            viewModel.onInitial()
        } else if (uiState.isLoading) {
            LoadingContent()
        } else if (uiState.isSuccess) {
            SignInContent(
                signInState = uiState.state(),
                onPhoneNumberChanged = { phoneNumber -> viewModel.onPhoneNumberChanged(phoneNumber) },
                onSendPhone = {
                    viewModel.onSendPhone { phone -> onOpenConfirmPhone(phone) }
                }
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
fun SignInContent(
    signInState: SignInState,
    onPhoneNumberChanged: (String) -> Unit,
    onSendPhone: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.sign_in_title),
            style = Typography.bodyLarge,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_big))
        )

        Spacer(
            modifier = Modifier
                .size(size = dimensionResource(id = R.dimen.padding_big))
        )

        MaterialCountryCodePicker(
            text = signInState.phoneNumber,
            phonenumbertextstyle = Typography.titleLarge,
            phonehintnumbertextstyle = Typography.titleLarge,
            searchFieldPlaceHolderTextStyle = Typography.titleLarge,
            searchFieldTextStyle = Typography.titleLarge,
            countrytextstyle = Typography.titleLarge,
            dialogcountrycodetextstyle = Typography.titleLarge,
            countrycodetextstyle = Typography.titleLarge,
            onValueChange = onPhoneNumberChanged,
            colors = ccpDefaultColors(),
            defaultCountry = getLibCountries().single { it.countryCode == getDefaultLangCode(LocalContext.current) },
            pickedCountry = { }
        )

        Spacer(
            modifier = Modifier
                .size(size = dimensionResource(id = R.dimen.padding_medium))
        )

        OutlinedButton(
            onClick = { onSendPhone() }
        ) {
            Text(
                text = stringResource(id = R.string.send_code),
                style = Typography.labelSmall
            )
        }
    }
}