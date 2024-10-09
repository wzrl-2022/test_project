package com.example.testproject.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.testproject.ui.theme.PurpleGrey40
import com.example.testproject.ui.theme.Typography

@Composable
fun TextLabel(
    text: String,
    label: String,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = horizontalAlignment,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = Typography.titleLarge
        )

        Text(
            text = label,
            style = Typography.labelSmall
        )
    }
}

@Composable
fun EditTextLabel(
    text: String,
    onTextChanged: (String) -> Unit = { },
    label: String,
    highlight: Boolean = false,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = horizontalAlignment,
        modifier = modifier
    ) {
        BasicTextField(
            value = text,
            onValueChange = onTextChanged,
            textStyle = Typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .bottomLine(
                    strokeWidth = 1.dp,
                    color = if (highlight) Color.Red else PurpleGrey40
                )
        )

        Text(
            text = label,
            style = Typography.labelSmall,
            color = if (highlight) Color.Red else PurpleGrey40
        )
    }
}