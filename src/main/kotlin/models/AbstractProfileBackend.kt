package models

import models.profile.*

interface AbstractProfileBackend {
    fun postStudentProfile(profile: StudentProfile)
    fun getStudentProfile(id: Int): StudentProfile

    fun postInstructorProfile(profile: InstructorProfile)
    fun getInstructorProfile(id: Int): InstructorProfile
}
