package db.dao

import org.jetbrains.exposed.dao.id.LongIdTable

object Achievements : LongIdTable() {
    val profileId = entityId("profile_id", Profiles)
    val type = varchar("type", 256)
    val name = text("name")
    val description = text("description")
    val date = varchar("date", 20)
}