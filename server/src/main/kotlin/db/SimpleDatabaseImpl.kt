package db

import db.dao.ProfileType
import db.dao.Profiles
import db.dao.Tags
import models.Tag
import org.jetbrains.exposed.sql.select

object SimpleDatabaseImpl : SimpleDatabase {
    override fun getStudentsIDByTag(tags: List<Tag>): List<Long> {
        return Tags.innerJoin(Profiles).slice(Tags.profileId).select {
            Tags.tag inList tags
            Profiles.profileType eq ProfileType.Student
        }.withDistinct().map { it[Tags.profileId].value }.toList()
    }

    override fun getInstructorsIDByTag(tags: List<Tag>): List<Long> {
        return Tags.innerJoin(Profiles).slice(Tags.profileId).select {
            Tags.tag inList tags
            Profiles.profileType eq ProfileType.Instructor
        }.withDistinct().map { it[Tags.profileId].value }.toList()
    }
}