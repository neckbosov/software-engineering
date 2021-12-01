package ui.chat

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import models.chat.Message
import ui.SimpleAppInfo

@OptIn(ExperimentalUnitApi::class)
@Composable
@Preview
fun Chat(appInfo: SimpleAppInfo, chatId: Long?) {
    if (chatId == null) {
        return Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {}
    }
    val scope = rememberCoroutineScope()
    val message = remember { mutableStateOf("") }
    val messages = remember { mutableStateOf(listOf<Message>()) }

    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) { scrollState.animateScrollTo(10000) }

    scope.launch {
        messages.value = appInfo.client.getMessages(chatId, 0, 100)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Column(modifier = Modifier.fillMaxWidth().weight(1f).padding(5.dp).verticalScroll(scrollState)) {
            messages.value.forEach { message ->
                val alignment = if (message.senderId == 0L) Alignment.Start else Alignment.End

                Card(
                    modifier = Modifier.widthIn(max = 340.dp).align(alignment),
                    shape = cardShapeFor(message.senderId == 0L), // 3
                    backgroundColor = when (message.senderId) {
                        0L -> MaterialTheme.colors.primary
                        else -> MaterialTheme.colors.secondary
                    },
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = message.content,
                        color = when (message.senderId) {
                            0L -> MaterialTheme.colors.onPrimary
                            else -> MaterialTheme.colors.onSecondary
                        },
                        fontSize = TextUnit(20f, TextUnitType.Sp)
                    )
                }
                Spacer(Modifier.heightIn(5.dp))
            }
        }
        Row {
            TextField(
                modifier = Modifier.fillMaxWidth().weight(1f),
                value = message.value,
                onValueChange = { message.value = it },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                appInfo.currentId?.let { appInfo.client.addMessage(chatId, it, message.value) }
                                messages.value = appInfo.client.getMessages(chatId, 0, 100)
                                message.value = ""
                            }
                        },
                        modifier = Modifier.requiredSize(50.dp)
                            .border(3.dp, MaterialTheme.colors.primary, shape = CircleShape)
                    ) {
                        Icon(Icons.Default.Send, contentDescription = "content description", tint = MaterialTheme.colors.primary)
                    }
                }
            )
        }
    }
}

@Composable
fun cardShapeFor(isMine: Boolean): Shape {
    val roundedCorners = RoundedCornerShape(16.dp)
    return when {
        isMine -> roundedCorners.copy(bottomStart = CornerSize(0))
        else -> roundedCorners.copy(bottomEnd = CornerSize(0))
    }
}