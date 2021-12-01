package backend.api.authaccess

import models.auth.AccountIsBannedError
import models.auth.NotAuthorizedError

data class AuthFlags(val profileId: Long, val isAdmin: Boolean, val isModerator: Boolean, val isBanned: Boolean) {
    fun notBannedOrThrow() {
        if (isBanned) {
            throw AccountIsBannedError
        }
    }
}