package db.dao

import org.jetbrains.exposed.dao.id.LongIdTable

object Instructors : LongIdTable() {
    val profileId = entityId("profile_id", Profiles).uniqueIndex().references(Profiles.id)
    val degree = varchar("degree", 256)
}