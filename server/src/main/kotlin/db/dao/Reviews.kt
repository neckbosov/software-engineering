package db.dao

import org.jetbrains.exposed.dao.id.LongIdTable

object Reviews : LongIdTable() {
    val userId = entityId("user_id", Profiles).references(Profiles.id)
    val reviewerId = entityId("reviewer_id", Profiles).references(Profiles.id)
    val date = varchar("date", 20)
    val body = text("body")
}