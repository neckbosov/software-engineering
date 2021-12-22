import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import models.profile.UserProfile
import ui.SimpleAppInfo
import ui.chat.ChatState
import ui.profile.view.ProfileViewState
import ui.search.SearchState
import ui.utils.PictureView
import ui.utils.loadNetworkImage

@Composable
fun MenuBar(
    appInfo: SimpleAppInfo,
    modifier: Modifier = Modifier,
    block: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    var myProfile by remember { mutableStateOf<UserProfile?>(null) }
    scope.launch {
        myProfile = appInfo.client.getProfile(appInfo.currentId!!)
    }
    Row(modifier = modifier.fillMaxSize(1f)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxHeight(1f)
                .width(64.dp)
                .height(64.dp)
                .background(Color.LightGray)
        ) {
            IconButton(
                onClick = { appInfo.currentState.value = ProfileViewState(appInfo.currentId!!) },
                modifier = Modifier
            ) {
                val picture = myProfile?.avatarURL?.let { loadNetworkImage(it) }
                PictureView(
                    picture,
                    modifier = Modifier
                        .background(Color.Transparent)
                        .clip(CircleShape)
                        .border(1.dp,
                            Color.DarkGray,
                            CircleShape
                        )
                )
            }
            IconButton(
                onClick = { appInfo.currentState.value = SearchState() },
                modifier = Modifier
            ) {
                Icon(Icons.Filled.Search, "search")
            }
            IconButton(
                onClick = { appInfo.currentState.value = ChatState(null) },
                modifier = Modifier
            ) {
                Icon(Icons.Filled.ChatBubbleOutline, "chats")
            }
        }
        Divider(
            color = Color.DarkGray,
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )
        block()
    }
}