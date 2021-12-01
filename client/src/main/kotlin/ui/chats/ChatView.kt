package ui.chats

import MenuBar
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.SimpleAppInfo

@Composable
fun ChatView(appInfo: SimpleAppInfo, chatId: Int?, modifier: Modifier = Modifier) {
    MenuBar(appInfo) {
        Row(modifier = Modifier.fillMaxSize(1f)) {
            ChatListView(appInfo, chatId, modifier = Modifier.fillMaxHeight(1f).fillMaxWidth(0.3f))
            Divider(
                color = Color.DarkGray,
                modifier = Modifier.width(1.dp).fillMaxHeight(1f)
            )
            ChatWindowView(appInfo, chatId)
        }
    }
}