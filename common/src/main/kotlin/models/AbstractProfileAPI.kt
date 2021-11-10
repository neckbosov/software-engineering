package models

import models.profile.InstructorProfile
import models.profile.StudentProfile
import models.profile.UserProfile

interface AbstractProfileAPI {
    suspend fun updateStudentProfile(id: Long, profile: StudentProfile)
    suspend fun getStudentProfile(id: Long): StudentProfile

    suspend fun updateInstructorProfile(id: Long, profile: InstructorProfile)
    suspend fun getInstructorProfile(id: Long): InstructorProfile

    suspend fun getIdByEmail(email: String): Long
    suspend fun getProfile(id: Long): UserProfile
}