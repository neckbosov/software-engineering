package backend.api

import db.dao.Chats
import db.dao.Messages
import models.AbstractChatAPI
import models.chat.Chat
import models.chat.Message
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

@Suppress("RemoveRedundantQualifierName")
class SimpleChatAPI : AbstractChatAPI {
    init {
        transaction {
            SchemaUtils.create(Chats, Messages)
        }
    }

    override suspend fun addChat(userId1: Long, userId2: Long): Chat {
        val chat = newSuspendedTransaction {
            Chats.insert { chat ->
                chat[Chats.userId1] = userId1
                chat[Chats.userId2] = userId2
            }
        }
        val id = chat[Chats.id]

        return Chat(userId1, userId2, id.value, 0)
    }

    override suspend fun addMessage(senderId: Long, chatId: Long, content: String): Message {
        val (msgId, msgPos) = newSuspendedTransaction {
            val msgCnt = Chats.select { Chats.id.eq(chatId) }.first()[Chats.messagesCnt]
            Chats.update({ Chats.id.eq(chatId) }) { chat ->
                chat[messagesCnt] = msgCnt + 1
            }

            val msgId = Messages.insert { msg ->
                msg[Messages.chatId] = chatId
                msg[Messages.pos] = msgCnt
                msg[Messages.content] = content
                msg[Messages.senderId] = senderId
            }[Messages.id].value

            Pair(msgId, msgCnt)
        }

        return Message(chatId, senderId, content, msgId, msgPos)
    }

    override suspend fun getMessageById(messageId: Long): Message {
        val msg = newSuspendedTransaction { Messages.select { Messages.id.eq(messageId) }.first() }

        return Message(
            msg[Messages.chatId].value,
            msg[Messages.senderId].value,
            msg[Messages.content],
            messageId,
            msg[Messages.pos]
        )
    }

    override suspend fun getChatById(chatId: Long): Chat {
        val chat = newSuspendedTransaction { Chats.select { Chats.id.eq(chatId) }.first() }

        return Chat(chat[Chats.userId1].value, chat[Chats.userId2].value, chatId, chat[Chats.messagesCnt])
    }

    override suspend fun getChatsByUserId(userId: Long): List<Chat> {
        return newSuspendedTransaction {
            Chats.select {
                Chats.userId1.eq(userId).or(Chats.userId2.eq(userId))
            }.toList()
        }.map { chat ->
            Chat(chat[Chats.userId1].value, chat[Chats.userId2].value, chat[Chats.id].value, chat[Chats.messagesCnt])
        }
    }

    override suspend fun getMessages(chatId: Long, startPos: Long, endPos: Long): List<Message> {
        return newSuspendedTransaction {
            Messages.select {
                Messages.chatId.eq(chatId)
                    .and(
                        Messages.pos.greaterEq(startPos)
                            .and(Messages.pos.less(endPos))
                    )
            }.toList()
        }.map { msg ->
            Message(
                msg[Messages.chatId].value,
                msg[Messages.senderId].value,
                msg[Messages.content],
                msg[Messages.id].value,
                msg[Messages.pos]
            )
        }
    }
}