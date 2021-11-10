package models

import models.profile.InstructorProfile
import models.profile.StudentProfile
import models.profile.UserProfile

interface AbstractProfileClient {
    suspend fun updateStudentProfile(id: Long, profile: StudentProfile)
    suspend fun getStudentProfile(id: Long): StudentProfile

    suspend fun updateInstructorProfile(id: Long, profile: InstructorProfile)
    suspend fun getInstructorProfile(id: Long): InstructorProfile

    suspend fun getIdByEmail(email: String): Long
    suspend fun getProfile(id: Long): UserProfile

    suspend fun searchStudentsByTags(tags: Tags): List<StudentProfile>
    suspend fun searchInstructorsByTags(tags: Tags): List<InstructorProfile>
}