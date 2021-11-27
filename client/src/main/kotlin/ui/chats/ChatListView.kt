package ui.chats

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import models.profile.InstructorProfile
import models.profile.StudentProfile
import ui.SimpleAppInfo
import ui.profile.view.ProfileViewState
import ui.search.SearchStudentView
import ui.search.SearchTutorView
import ui.utils.BoxWithVerticalScroll
import ui.utils.PictureView
import ui.utils.loadNetworkImage

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChatListView(appInfo: SimpleAppInfo, chatId: Int?, modifier: Modifier = Modifier) {
    Surface(modifier = modifier, color = Color(0xFFCCCCCC)) {
        BoxWithVerticalScroll(Modifier.fillMaxSize(1f)) {
            val availableChats = tmpChatList /* appInfo.client.getChats() */
            Column(modifier = Modifier.fillMaxSize(1f)) {
                availableChats.forEach {
                    PreviewChatView(appInfo, it, chatId)
                    Divider()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PreviewChatView(appInfo: SimpleAppInfo, chat: TMPChat, openChatId: Int?) {
    val coroutineScope = rememberCoroutineScope()
    val picture: MutableState<ImageBitmap?> = mutableStateOf(null)
    coroutineScope.launch {
        delay(10L)
        picture.value = chat.avatarURL?.let { loadNetworkImage(chat.avatarURL) }
    }
    ListItem(
        text = { Text(chat.name) },
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
