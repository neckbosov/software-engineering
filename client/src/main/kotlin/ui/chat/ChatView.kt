package ui.chat

import MenuBar
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import ui.SimpleAppInfo

@Composable
fun ChatView(appInfo: SimpleAppInfo) {
    MenuBar(appInfo) {
        Row {
            ChatList()
            Chat()
        }
    }
}