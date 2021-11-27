package api

import models.chat.Chat
import models.chat.Message

interface AbstractChatAPI {
    suspend fun addChat(userId1: Long, userId2: Long): Chat

    suspend fun addMessage(chatId: Long, senderId: Long, content: String): Message

    suspend fun getMessageById(messageId: Long): Message

    suspend fun getChatById(chatId: Long): Chat

    suspend fun getChatsByUserId(userId: Long): List<Chat>

    /**
     * Returns messages from `startPos` until `endPos`.
     * To get all chat messages: startPos = 0, endPos = chat.messages_cnt
     */
    suspend fun getMessages(chatId: Long, startPos: Long, endPos: Long): List<Message>
}