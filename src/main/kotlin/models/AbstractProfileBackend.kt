package models

import models.profile.*

interface AbstractProfileBackend {
    fun postStudentProfile(id: Int, profile: StudentProfile)
    fun getStudentProfile(id: Int): StudentProfile

    fun postInstructorProfile(id: Int, profile: InstructorProfile)
    fun getInstructorProfile(id: Int): InstructorProfile
}
