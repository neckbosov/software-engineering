package backend.api.authaccess

import backend.api.SimpleProfileAPI
import backend.storage.AuthorizationStorage
import models.auth.NotAuthorizedError
import models.profile.InstructorProfile
import models.profile.StudentProfile
import models.profile.UserProfile

class AuthorizedProfileAPI(val api: SimpleProfileAPI) {
    suspend fun updateStudentProfile(agentId: Long, id: Long, profile: StudentProfile) {
        val flags = AuthorizationStorage.getFlags(agentId)
        flags.notBannedOrThrow()
        if (agentId != id && !flags.isAdmin)
            throw NotAuthorizedError
        return api.updateStudentProfile(id, profile)
    }

    suspend fun getStudentProfile(agentId: Long, id: Long): StudentProfile {
        val flags = AuthorizationStorage.getFlags(agentId)
        flags.notBannedOrThrow()
        return api.getStudentProfile(id)
    }

    suspend fun updateInstructorProfile(agentId: Long, id: Long, profile: InstructorProfile) {
        val flags = AuthorizationStorage.getFlags(agentId)
        flags.notBannedOrThrow()
        if (agentId != id && !flags.isAdmin)
            throw NotAuthorizedError
        return api.updateInstructorProfile(id, profile)
    }

    suspend fun getInstructorProfile(agentId: Long, id: Long): InstructorProfile {
        val flags = AuthorizationStorage.getFlags(agentId)
        flags.notBannedOrThrow()
        return api.getInstructorProfile(id)
    }

    suspend fun getIdByEmail(agentId: Long, email: String): Long {
        val flags = AuthorizationStorage.getFlags(agentId)
        flags.notBannedOrThrow()
        return api.getIdByEmail(email)
    }

    suspend fun getProfile(agentId: Long, id: Long): UserProfile {
        val flags = AuthorizationStorage.getFlags(agentId)
        flags.notBannedOrThrow()
        return api.getProfile(id)
    }
}