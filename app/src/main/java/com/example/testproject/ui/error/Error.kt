package com.example.testproject.ui.error

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.testproject.R
import com.example.testproject.ui.theme.Typography

@Composable
fun ErrorContent(
    header: String,
    message: String,
    actionText: String,
    onAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = header,
            style = Typography.titleLarge
        )

        Spacer(
            modifier = Modifier
                .size(size = dimensionResource(id = R.dimen.padding_medium))
        )

        Text(
            text = message,
            style = Typography.labelMedium
        )
        
        Spacer(
            modifier = Modifier
                .size(size = dimensionResource(id = R.dimen.padding_medium))
        )

        OutlinedButton(
            onClick = onAction,
            modifier = Modifier
                .widthIn(min = 80.dp)
        ) {
            Text(
                text = actionText,
                style = Typography.labelSmall
            )
        }
    }
}