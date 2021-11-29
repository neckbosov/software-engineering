package db

import db.dao.Profiles
import db.dao.Tags
import models.ProfileType
import models.Tag
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object SimpleDatabaseImpl : SimpleDatabase {
    init {
        val dbHost = System.getenv("DB_HOST") ?: "localhost"
        val dbPort = System.getenv("DB_PORT") ?: "5432"
        val dbName = System.getenv("DB_NAME") ?: "postgres"
        val dbUser = System.getenv("DB_USER") ?: "postgres"
        val dbPassword = System.getenv("DB_PASSWORD") ?: "postgres"
        Database.connect(
            "jdbc:postgresql://$dbHost:$dbPort/$dbName",
            driver = "org.postgresql.Driver",
            user = dbUser,
            password = dbPassword
        )
    }

    override suspend fun getStudentsIDByTag(tags: List<Tag>): List<Long> {
        return newSuspendedTransaction {
            Tags.innerJoin(Profiles).slice(Tags.profileId).select {
                (Tags.tag inList tags) and (Profiles.profileType eq ProfileType.Student)
            }.withDistinct().map { it[Tags.profileId].value }.toList()
        }
    }

    override suspend fun getInstructorsIDByTag(tags: List<Tag>): List<Long> {
        return newSuspendedTransaction {
            Tags.innerJoin(Profiles).slice(Tags.profileId).select {
                (Tags.tag inList tags) and (Profiles.profileType eq ProfileType.Instructor)
            }.withDistinct().map { it[Tags.profileId].value }.toList()
        }
    }


    override suspend fun getTagsByPrefix(prefix: String): List<Tag> {
        return newSuspendedTransaction {
            Tags.slice(Tags.tag).select {
                Tags.tag like "$prefix%"
            }.withDistinct().map { it[Tags.tag] }.toList()
        }
    }
}