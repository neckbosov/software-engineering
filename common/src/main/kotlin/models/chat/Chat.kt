package models.chat

import kotlinx.serialization.Serializable


/**
 * Messages for the chat should be retrieved via `getMessages` method
 * of implementation of AbstractChatBackend
 */
@Serializable
data class Chat(
    val user1: Long,
    val user2: Long,
    val id: Long = 0,
    val messages_cnt: Long = 0,
)

/**
 * `pos` is the position of the message in chat.
 * Messages are ordered from oldest to newest.
 * So the first message that was sent to the chat has id = 0
 */
@Serializable
data class Message(
    val chatId: Long,
    val senderId: Long,
    val content: String,
    val id: Long = 0,
    val pos: Long = 0,
)