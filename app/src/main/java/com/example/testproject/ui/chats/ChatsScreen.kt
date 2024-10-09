package com.example.testproject.ui.chats

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.testproject.repository.chats.Chat
import com.example.testproject.ui.theme.PurpleGrey40
import com.example.testproject.ui.theme.Typography
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsScreen(
    onOpenProfile: () -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = hiltViewModel<ChatsViewModel>()

    val chatsState by viewModel.chatsFlow.collectAsState()

    if (chatsState.isInitial) {
        viewModel.onInitial()
    } else {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    DrawerContent(
                        chatsState = chatsState.state(),
                        onOpenProfile = onOpenProfile,
                        onChatSelect = { index ->
                            scope.launch {
                                drawerState.close()
                                viewModel.onChatSelected(index)
                            }
                        },
                        onLogout = onLogout
                    )
                }
            }
        ) {
            val selectedChat = chatsState.state().chats[chatsState.state().selected]

            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = selectedChat.title,
                                style = Typography.bodyLarge,
                                textAlign = TextAlign.Center
                            )
                        },
                        navigationIcon = {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = null,
                                modifier = Modifier.clickable {
                                    scope.launch {
                                        drawerState.apply {
                                            if (isClosed) open() else close()
                                        }
                                    }
                                }
                            )
                        }
                    )
                },
                modifier = modifier
            ) { innerPadding ->
                ChatContent(
                    chat = selectedChat,
                    onSendMessage = { message -> viewModel.onSendMessage(message) },
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun DrawerContent(
    chatsState: ChatsState,
    onOpenProfile: () -> Unit,
    onChatSelect: (Int) -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        ProfileContent(
            user = chatsState.profile,
            onOpenProfile = onOpenProfile,
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier
            .size(size = dimensionResource(R.dimen.padding_small))
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = PurpleGrey40,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier
            .size(size = dimensionResource(R.dimen.padding_big))
        )

        ChatListContent(
            chats = chatsState.chats,
            selected = chatsState.selected,
            onSelected = onChatSelect,
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
                .weight(1f)
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = PurpleGrey40,
            modifier = Modifier
                .fillMaxWidth()
        )

        Text(
            text = "Logout",
            style = Typography.bodyLarge,
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
                .clickable { onLogout() }
        )
    }
}

@Composable
fun ProfileContent(
    user: User,
    onOpenProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row {
            AsyncImage(
                model = "https://plannerok.ru/${user.avatars?.avatar}",
                placeholder = painterResource(R.drawable.ic_launcher_background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )

            Column(
                modifier = Modifier
                    .height(64.dp)
                    .padding(
                        PaddingValues(
                            horizontal = dimensionResource(R.dimen.padding_medium),
                            vertical = 0.dp
                        )
                    )
            ) {
                Text(
                    text = user.name,
                    style = Typography.titleLarge
                )

                Spacer(modifier = Modifier
                    .weight(1f)
                )

                Text(
                    text = "@${user.userName}",
                    style = Typography.titleLarge
                )
            }
        }

        Spacer(modifier = Modifier
            .size(size = dimensionResource(R.dimen.padding_big))
        )

        OutlinedButton(
            onClick = onOpenProfile,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.label_open_profile),
                style = Typography.labelSmall
            )
        }
    }
}

@Composable
fun ChatListContent(
    chats: List<Chat>,
    selected: Int,
    onSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Chats",
            style = Typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(
            modifier = Modifier
                .size(dimensionResource(R.dimen.padding_medium))
        )

        LazyColumn {
            itemsIndexed(chats) { index, chat ->
                NavigationDrawerItem(
                    label = {
                        Text(
                            text = chat.title,
                            style = Typography.bodyLarge
                        )
                    },
                    selected = index == selected,
                    onClick = { onSelected(index) },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}