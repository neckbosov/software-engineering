package back

import models.AbstractProfileBackend
import models.profile.InstructorProfile
import models.profile.StudentProfile


class SimpleProfileBackend: AbstractProfileBackend{
    override fun postStudentProfile(id: Int, profile: StudentProfile) {
        throw NotImplementedError()
    }
    override fun  getStudentProfile(id: Int): StudentProfile{
        throw NotImplementedError()
    }
    override fun  postInstructorProfile(id: Int, profile: InstructorProfile) {
        throw NotImplementedError()
    }
    override fun  getInstructorProfile(id: Int): InstructorProfile{
        throw NotImplementedError()
    }
}

