package ui.chats

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ui.SimpleAppInfo

@Composable
fun ChatWindowView(appInfo: SimpleAppInfo, chatId: Int?, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(1f).clickable {  }) {
        Text("Chat with id = $chatId")
    }
    //TODO("Dear Olya. Please, implement this function...")
}