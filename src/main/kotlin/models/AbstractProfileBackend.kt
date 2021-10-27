package models

import models.profile.InstructorProfile
import models.profile.StudentProfile
import models.profile.UserProfile

interface AbstractProfileBackend {
    fun postStudentProfile(id: Long, profile: StudentProfile)
    fun getStudentProfile(id: Long): StudentProfile

    fun postInstructorProfile(id: Long, profile: InstructorProfile)
    fun getInstructorProfile(id: Long): InstructorProfile

    fun getIdByEmail(email: String): Long?
    fun getProfile(id: Long): UserProfile
    fun postProfile(profile: UserProfile): Long
}
