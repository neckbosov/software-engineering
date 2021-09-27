package dao

import org.jetbrains.exposed.dao.id.LongIdTable

object Profiles : LongIdTable() {
    val avatarUrl = text("avatar_url").nullable()
    val firstName = varchar("first_name", 50)
    val lastName = varchar("last_name", 50)
    val patronymic = varchar("patronymic", 50).nullable()
    val currentJobId = entityId("current_job_id", Jobs).uniqueIndex()
    val isActive = bool("is_active")
}