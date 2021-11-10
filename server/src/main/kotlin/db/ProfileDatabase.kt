package db

import models.profile.*

interface ProfileDatabase {
    fun getStudentsProfile(): List<StudentProfile>

    fun getInstructorsProfile(): List<InstructorProfile>
}
