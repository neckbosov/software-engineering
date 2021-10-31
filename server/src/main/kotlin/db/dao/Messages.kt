package db.dao

import org.jetbrains.exposed.dao.id.LongIdTable

object Messages : LongIdTable() {
    val chatId = entityId("chat_id", Chats)
    val senderId = entityId("sender_id", Profiles)
    val pos = long("pos")
    val content = text("content")
}