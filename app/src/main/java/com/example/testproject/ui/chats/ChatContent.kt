package com.example.testproject.ui.chats

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.testproject.R
import com.example.testproject.helpers.format
import com.example.testproject.repository.chats.Chat
import com.example.testproject.ui.theme.Pink40
import com.example.testproject.ui.theme.Pink80
import com.example.testproject.ui.theme.PurpleGrey40
import com.example.testproject.ui.theme.PurpleGrey80
import com.example.testproject.ui.theme.Typography

@Composable
fun ChatContent(
    chat: Chat,
    onSendMessage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Column {
            val messages = chat.messages.toMutableStateList()
            LazyColumn(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.weight(1f)
            ) {
                itemsIndexed(messages) { _, message ->
                    Row {
                        Spacer(
                            modifier = Modifier
                                .size(if (message.byMe) dimensionResource(R.dimen.padding_giant) else 0.dp)
                        )

                        MessageContent(
                            message,
                            modifier = Modifier
                                .weight(1f)
                                .padding(dimensionResource(R.dimen.padding_medium))
                        )

                        Spacer(
                            modifier = Modifier
                                .size(if (message.byMe) 0.dp else dimensionResource(R.dimen.padding_giant))
                        )
                    }
                }
            }

            var inputMessage by remember { mutableStateOf("") }
            OutlinedTextField(
                value = inputMessage,
                onValueChange = { inputMessage = it },
                trailingIcon = {
                    Icon(
                        Icons.AutoMirrored.Default.Send,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                onSendMessage(inputMessage)
                                inputMessage = ""
                            }
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions {
                    onSendMessage(inputMessage)
                    inputMessage = ""
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun MessageContent(
    message: Chat.Message,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = if (message.byMe) PurpleGrey80 else Pink80,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = if (message.byMe) PurpleGrey40 else Pink40,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Text(
                text = message.author.nickname,
                style = Typography.labelSmall,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier
                .size(dimensionResource(R.dimen.padding_small))
            )

            Text(
                text = message.message,
                style = Typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier
                .size(dimensionResource(R.dimen.padding_small))
            )

            Text(
                text = message.date.format("dd-MM-yyyy HH:mm"),
                style = Typography.labelSmall,
                modifier = Modifier
                    .align(Alignment.End)
            )
        }
    }
}