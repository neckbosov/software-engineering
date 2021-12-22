package db.dao

import org.jetbrains.exposed.dao.id.LongIdTable

object Messages : LongIdTable() {
    val chatId = entityId("chat_id", Chats).references(Chats.id)
    val senderId = entityId("sender_id", Profiles).references(Profiles.id)
    val pos = long("pos")
    val content = text("content")
    val timestamp = text("timestamp")
}