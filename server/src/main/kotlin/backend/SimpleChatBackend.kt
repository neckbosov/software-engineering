package backend

import db.dao.*
import models.AbstractChatBackend
import models.chat.Chat
import models.chat.Message
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class SimpleChatBackend : AbstractChatBackend {
    init {
        val dbHost = System.getenv("DB_HOST") ?: "localhost"
        val dbPort = System.getenv("DB_PORT") ?: "5432"
        val dbName = System.getenv("DB_NAME") ?: "postgres"
        val dbUser = System.getenv("DB_USER") ?: "postgres"
        val dbPassword = System.getenv("DB_PASSWORD") ?: "postgres"
        Database.connect(
            "jdbc:postgresql://$dbHost:$dbPort/$dbName",
            driver = "org.postgresql.Driver",
            user = dbUser,
            password = dbPassword
        )
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(Chats, Messages)
        }
    }

    override fun addChat(userId1: Long, userId2: Long): Chat {
        val chat = Chats.insert { chat ->
            chat[Chats.userId1] = userId1
            chat[Chats.userId2] = userId2
        }
        val id = chat[Chats.id];

        return Chat(userId1, userId2, id.value, 0)
    }

    override fun addMessage(chatId: Long, senderId: Long, content: String): Message {
        val (msgId, msgPos) = transaction {
            val msgCnt = Chats.select { Chats.id.eq(chatId) }.first()[Chats.messagesCnt]
            Chats.update({ Chats.id.eq(chatId) }) { chat ->
                chat[messagesCnt] = msgCnt + 1;
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

    override fun getMessageById(messageId: Long): Message {
        val msg = Messages.select { Messages.id.eq(messageId) }.first()

        return Message( msg[Messages.chatId].value, msg[Messages.senderId].value, msg[Messages.content], messageId, msg[Messages.pos])
    }

    override fun getChatById(chatId: Long): Chat {
        val chat = Chats.select { Chats.id.eq(chatId) }.first()

        return Chat(chat[Chats.userId1].value, chat[Chats.userId2].value, chatId, chat[Chats.messagesCnt])
    }

    override fun getChatsByUserId(userId: Long): List<Chat> {
        return Chats.select {
            Chats.userId1.eq(userId).or(Chats.userId2.eq(userId))
        }.map { chat ->
            Chat(chat[Chats.userId1].value, chat[Chats.userId2].value, chat[Chats.id].value, chat[Chats.messagesCnt])
        }
    }

    override fun getMessages(chatId: Long, startPos: Long, endPos: Long): List<Message> {
        return Messages.select {
            Messages.chatId.eq(chatId)
                .and(Messages.pos.greaterEq(startPos)
                    .and(Messages.pos.less(endPos)))
        }.map { msg ->
            Message(msg[Messages.chatId].value, msg[Messages.senderId].value, msg[Messages.content], msg[Messages.id].value, msg[Messages.pos])
        }
    }
}