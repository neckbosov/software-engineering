package db.dao

import org.jetbrains.exposed.dao.id.LongIdTable

object Chats : LongIdTable() {
    val userId1 = entityId("user1_id", Profiles)
    val userId2 = entityId("user2_id", Profiles)
    val messagesCnt = long("msg_cnt")
}