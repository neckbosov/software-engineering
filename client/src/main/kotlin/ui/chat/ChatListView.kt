package ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import models.profile.UserProfile
import ui.SimpleAppInfo
import ui.utils.BoxWithVerticalScroll
import ui.utils.PictureView
import ui.utils.loadNetworkImage

data class ChatInfo(
    val id: Long,
    val user1: UserProfile,
    val user2: UserProfile
)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChatListView(appInfo: SimpleAppInfo, chatId: Long?, modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    val availableChats = remember { mutableStateOf(listOf<ChatInfo>()) }

    scope.launch {
        val chats = appInfo.currentId?.let { appInfo.client.getChatsByUserId(it) } ?: listOf()
        availableChats.value = chats.map { chat ->
            if (chat.user1 == appInfo.currentId) {
                ChatInfo(
                    chat.id,
                    appInfo.client.getProfile(chat.user1),
                    appInfo.client.getProfile(chat.user2)
                )
            } else {
                ChatInfo(
                    chat.id,
                    appInfo.client.getProfile(chat.user2),
                    appInfo.client.getProfile(chat.user1)
                )
            }
        }
    }
    Surface(modifier = modifier, color = Color(0xFFCCCCCC)) {
        BoxWithVerticalScroll(Modifier.fillMaxSize(1f)) {
            Column(modifier = Modifier.fillMaxSize(1f)) {
                availableChats.value.forEach {
                    PreviewChatView(appInfo, it, chatId)
                    Divider()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PreviewChatView(appInfo: SimpleAppInfo, chat: ChatInfo, openChatId: Long?) {
    val coroutineScope = rememberCoroutineScope()
    val picture: MutableState<ImageBitmap?> = mutableStateOf(null)
    coroutineScope.launch {
        delay(10L)
        picture.value = chat.user2.avatarURL?.let { loadNetworkImage(it) }
    }
    ListItem(
        text = { Text(chat.user2.name + " " + chat.user2.surname) },
        icon = {
            PictureView(
                picture.value,
                modifier = Modifier
                    .background(Color.Transparent)
                    .clip(CircleShape)
                    .size(50.dp)
                    .border(
                        1.dp,
                        Color.DarkGray,
                        CircleShape
                    )
            )
        },
        secondaryText = { Text("Message") },
        modifier = Modifier.fillMaxWidth().clickable {
            appInfo.currentState.value = ChatState(chat.id)
        }.background(
            if (chat.id == openChatId)
                Color.Gray
            else
                Color.Transparent
        )
    )
}
