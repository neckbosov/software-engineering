package db.dao

import org.jetbrains.exposed.dao.id.LongIdTable

object Students : LongIdTable() {
    val profileId = entityId("profile_id", Profiles).uniqueIndex().references(Profiles.id)
    val university = varchar("university", 256)
    val faculty = varchar("faculty", 256)
    val degree = varchar("step", 50)
    val course = integer("course")
    val from = varchar("from", 256)
    val to = varchar("to", 256)
    val gpa = decimal("gpa", 3, 2).nullable()
    val cvUrl = text("cv_url").nullable()
}