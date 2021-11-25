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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import models.chat.Message

@OptIn(ExperimentalUnitApi::class)
@Composable
@Preview
fun Chat() {
    val message = remember { mutableStateOf("") }
    val messages = listOf(
        Message(0, 0, "Hello!"),
        Message(0, 0, "How are you?"),
        Message(0, 1, "Hi! Fine, thanks. And you?"),
        Message(0, 0, "Me too"),
        Message(0, 1, "niice"),
        Message(0, 1, "pafpafpaf"),
        Message(0, 0, "Hello!"),
        Message(0, 0, "How are you?"),
        Message(0, 1, "Hi! Fine, thanks. And you?"),
        Message(0, 0, "Me too"),
        Message(0, 1, "niice"),
        Message(0, 1, "pafpafpaf"),
        Message(0, 0, "Hello!"),
        Message(0, 0, "How are you?"),
        Message(0, 1, "Hi! Fine, thanks. And you?"),
        Message(0, 0, "Me too"),
        Message(0, 1, "niice"),
        Message(0, 1, "pafpafpaf"),
    )

    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) { scrollState.animateScrollTo(10000) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Column(modifier = Modifier.fillMaxWidth().weight(1f).padding(5.dp).verticalScroll(scrollState)) {
            messages.forEach { message ->
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
                        onClick = { },
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