package dao

import org.jetbrains.exposed.dao.id.LongIdTable

object Projects : LongIdTable() {
    val teacherId = entityId("teacher_id", Teachers)
    val title = varchar("title", 256)
    val description = text("description")
}