package dao

import org.jetbrains.exposed.dao.id.LongIdTable

object Achievements : LongIdTable() {
    val profileId = entityId("profile_id", Profiles)
    val year = integer("year")
    val type = varchar("type", 256)
    val name = text("name")
}