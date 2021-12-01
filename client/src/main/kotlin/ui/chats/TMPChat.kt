package ui.chats

data class TMPChat(
    val id: Int,
    val name: String,
    val avatarURL: String?,
    val members: List<Int>,
    val messages: List<Pair<Int, String>>
)

val tmpChatList = listOf<TMPChat>(
    TMPChat(
        id = 1,
        name = "AO GLEBALO",
        avatarURL = "https://sun9-62.userapi.com/impg/fUfKMGZdRsANZYsBeyq2mSxvo8QJjDDC6eOGSA/GQwQxLmbJ8k.jpg?size=972x2160&quality=96&sign=e90c0b5ddf3ddfd6e42add16f9a40b0b&type=album",
        members = listOf(1, 2),
        messages = listOf(1 to "ебать", 2 to "пиздец")
    ),
    TMPChat(
        id = 2,
        name = "GLEBALO Private",
        avatarURL = "https://sun9-67.userapi.com/impg/sVJbgGxuqH9os5-FUDQBCos8zRB65HxEmUo6pQ/5-GDQSGJgbM.jpg?size=576x1280&quality=96&sign=a5ef7ab291abceaae06b1f70c53f2654&type=album",
        members = listOf(1, 2),
        messages = listOf(1 to "сука ебать", 2 to "сука пиздец")
    )
)