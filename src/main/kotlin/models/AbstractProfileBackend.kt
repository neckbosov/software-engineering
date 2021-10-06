package models

import models.profile.*

interface AbstractProfileBackend {
    fun postStudentProfile(id: Long, profile: StudentProfile)
    fun getStudentProfile(id: Long): StudentProfile

    fun postInstructorProfile(id: Long, profile: InstructorProfile)
    fun getInstructorProfile(id: Long): InstructorProfile
}
