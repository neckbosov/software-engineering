package dao

import org.jetbrains.exposed.dao.id.LongIdTable

object Profiles : LongIdTable() {
    val avatarUrl = varchar("avatar_url", 256).nullable()
    val firstName = varchar("first_name", 50)
    val lastName = varchar("last_name", 50)
    val currentJob = varchar("current_job", 256).nullable()
    val isActive = bool("is_active")
}