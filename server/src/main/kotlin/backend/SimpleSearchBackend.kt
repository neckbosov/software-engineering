package backend

import db.ProfileDatabase
import models.AbstractSearchBackend
import models.Tags
import models.profile.*

class SimpleSearchBackend(val database: ProfileDatabase): AbstractSearchBackend {
    override fun searchStudentsByTags(tags: Tags): List<StudentProfile> {
        // TODO
    }

    override fun searchInstructorsByTags(tags: Tags): List<InstructorProfile> {
        // TODO
    }
}
