package backend.api.authaccess

import api.AbstractChatAPI
import backend.storage.AuthorizationStorage
import db.dao.Chats.userId1
import db.dao.Chats.userId2
import db.dao.Messages.chatId
import models.auth.NotAuthorizedError
import models.chat.Chat
import models.chat.Message

class AuthorizedChatAPI(private val api: AbstractChatAPI) {
    suspend fun addChat(agentId: Long, userId1: Long, userId2: Long): Chat {
        val flags = AuthorizationStorage.getFlags(agentId)
        flags.notBannedOrThrow()
        if (agentId != userId1 && agentId != userId2 && !flags.isAdmin)
            throw NotAuthorizedError
        return api.addChat(userId1, userId2)
    }

    private suspend fun AuthFlags.canWriteToChatOrThrow(chatId: Long) {
        if (isAdmin)
            return
        val chat = api.getChatById(chatId)
        if (profileId != chat.user1 && profileId != chat.user2)
            throw NotAuthorizedError
    }

    suspend fun addMessage(agentId: Long, chatId: Long, content: String): Message {
        val flags = AuthorizationStorage.getFlags(agentId)
        flags.notBannedOrThrow()
        flags.canWriteToChatOrThrow(chatId)
        return api.addMessage(agentId, chatId, content)
    }

    private suspend fun AuthFlags.canReadChatOrThrow(chatId: Long) {
        if (isAdmin)
            return
        val chat = api.getChatById(chatId)
        if (profileId != chat.user1 && profileId != chat.user2)
            throw NotAuthorizedError
    }

    private suspend fun AuthFlags.canReadMessageOrThrow(messageId: Long) {
        if (isAdmin)
            return
        val message = api.getMessageById(messageId)
        canReadChatOrThrow(message.chatId)
    }

    suspend fun getMessageById(agentId: Long, messageId: Long): Message {
        val flags = AuthorizationStorage.getFlags(agentId)
        flags.notBannedOrThrow()
        flags.canReadMessageOrThrow(messageId)
        return api.getMessageById(messageId)
    }

    suspend fun getChatById(agentId: Long, chatId: Long): Chat {
        val flags = AuthorizationStorage.getFlags(agentId)
        flags.notBannedOrThrow()
        flags.canReadChatOrThrow(chatId)
        return api.getChatById(chatId)
    }

    suspend fun getChatByUserIds(agentId: Long, userId: Long): Chat {
        val flags = AuthorizationStorage.getFlags(agentId)
        flags.notBannedOrThrow()
        return api.getChatByUserIds(agentId, userId)
    }

    suspend fun getChatsByUserId(agentId: Long, userId: Long): List<Chat> {
        val flags = AuthorizationStorage.getFlags(agentId)
        println(agentId)
        println(userId)
        flags.notBannedOrThrow()
        if (agentId != userId && !flags.isAdmin)
            throw NotAuthorizedError
        return api.getChatsByUserId(userId)
    }

    suspend fun getMessages(agentId: Long, chatId: Long, startPos: Long, endPos: Long): List<Message> {
        val flags = AuthorizationStorage.getFlags(agentId)
        flags.notBannedOrThrow()
        flags.canReadChatOrThrow(chatId)
        return api.getMessages(chatId, startPos, endPos)
    }
}