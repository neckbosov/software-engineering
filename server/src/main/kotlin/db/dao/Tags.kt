package db.dao

import org.jetbrains.exposed.dao.id.LongIdTable

object Tags : LongIdTable() {
    val profileId = entityId("profile_id", Profiles).references(Profiles.id)
    val tag = varchar("tag", 50).index()
}