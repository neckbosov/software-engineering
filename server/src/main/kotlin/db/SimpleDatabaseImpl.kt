package db

import db.dao.Profiles
import db.dao.Tags
import models.ProfileType
import models.Tag
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object SimpleDatabaseImpl : SimpleDatabase {
    override suspend fun getStudentsIDByTag(tags: List<Tag>): List<Long> {
        return newSuspendedTransaction {
            Tags.innerJoin(Profiles).slice(Tags.profileId).select {
                Tags.tag inList tags
                Profiles.profileType eq ProfileType.Student
            }.withDistinct().map { it[Tags.profileId].value }.toList()
        }
    }

    override suspend fun getInstructorsIDByTag(tags: List<Tag>): List<Long> {
        return newSuspendedTransaction {
            Tags.innerJoin(Profiles).slice(Tags.profileId).select {
                Tags.tag inList tags
                Profiles.profileType eq ProfileType.Instructor
            }.withDistinct().map { it[Tags.profileId].value }.toList()
        }
    }
}