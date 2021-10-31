package models

import models.chat.Chat
import models.chat.Message

interface AbstractChatBackend {
    fun addChat(userId1: Long, userId2: Long): Chat

    fun addMessage(chatId: Long, senderId: Long, content: String): Message

    fun getMessageById(messageId: Long): Message

    fun getChatById(chatId: Long): Chat

    fun getChatsByUserId(userId: Long): List<Chat>

    /**
     * Returns messages from `startPos` until `endPos`.
     * To get all chat messages: startPos = 0, endPos = chat.messages_cnt
     */
    fun getMessages(chatId: Long, startPos: Long, endPos: Long): List<Message>
}