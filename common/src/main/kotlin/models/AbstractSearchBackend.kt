package models

import models.profile.*

interface AbstractSearchBackend {
    fun searchStudentsByTags(tags: Tags): List<StudentProfile>

    fun searchInstructorsByTags(tags: Tags): List<InstructorProfile>
}
