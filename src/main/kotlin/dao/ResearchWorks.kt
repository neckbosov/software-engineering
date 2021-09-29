package dao

import org.jetbrains.exposed.dao.id.LongIdTable

object ResearchWorks : LongIdTable() {
    val instructorId = entityId("instructor_id", Instructors)
    val title = varchar("title", 256)
    val description = text("description")
    val detailsURL = text("details_url")
}