package db.dao

import org.jetbrains.exposed.dao.id.LongIdTable

object Jobs : LongIdTable() {
    val profileId = entityId("profile_id", Profiles)
    val place = varchar("place", 256)
    val position = varchar("position", 256)
    val fromDate = varchar("from_date", 20)
    val toDate = varchar("to_date", 20)
}