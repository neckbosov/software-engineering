package backend.api

import api.AbstractProfileAPI
import api.AbstractSearchAPI
import db.SimpleDatabase
import models.Tag
import models.profile.InstructorProfile
import models.profile.StudentProfile

class SimpleSearchAPI(private val database: SimpleDatabase, private val profileAPI: AbstractProfileAPI) :
    AbstractSearchAPI {
    override suspend fun searchStudentsByTags(tags: List<Tag>): List<StudentProfile> {
        val studentIDs = database.getStudentsIDByTag(tags)
        return studentIDs.map {
            profileAPI.getProfile(it)
        }.filterIsInstance<StudentProfile>()
    }

    override suspend fun searchInstructorsByTags(tags: List<Tag>): List<InstructorProfile> {
        val instructorIDs = database.getInstructorsIDByTag(tags)
        return instructorIDs.map {
            profileAPI.getProfile(it)
        }.filterIsInstance<InstructorProfile>()
    }

    override suspend fun getTagsByPrefix(prefix: String): List<Tag> {
        return database.getTagsByPrefix(prefix)
    }
}