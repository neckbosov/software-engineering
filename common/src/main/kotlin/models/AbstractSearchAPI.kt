package models

import models.profile.InstructorProfile
import models.profile.StudentProfile

interface AbstractSearchAPI {
    suspend fun searchStudentsByTags(tags: Tags): List<StudentProfile>
    suspend fun searchInstructorsByTags(tags: Tags): List<InstructorProfile>
    suspend fun getTagsByPrefix(prefix: String): List<Tag>
}