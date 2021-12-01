package backend.api.authaccess

import api.AbstractSearchAPI
import backend.storage.AuthorizationStorage
import models.Tag
import models.Tags
import models.profile.InstructorProfile
import models.profile.StudentProfile

class AuthorizedSearchAPI(val api: AbstractSearchAPI) {
    suspend fun searchStudentsByTags(agentId: Long, tags: Tags): List<StudentProfile> {
        val flags = AuthorizationStorage.getFlags(agentId)
        flags.notBannedOrThrow()
        return api.searchStudentsByTags(tags)
    }

    suspend fun searchInstructorsByTags(agentId: Long, tags: Tags): List<InstructorProfile> {
        val flags = AuthorizationStorage.getFlags(agentId)
        flags.notBannedOrThrow()
        return api.searchInstructorsByTags(tags)
    }

    suspend fun getTagsByPrefix(agentId: Long, prefix: String): List<Tag> {
        val flags = AuthorizationStorage.getFlags(agentId)
        flags.notBannedOrThrow()
        return api.getTagsByPrefix(prefix)
    }
}