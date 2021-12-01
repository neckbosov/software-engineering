package db.dao

import org.jetbrains.exposed.dao.id.LongIdTable

object Authorization : LongIdTable() {
    val profileId = entityId("profile_id", Profiles).uniqueIndex().references(Profiles.id)
    val isAdmin = bool("is_admin")
    val isModerator = bool("is_moderator")
    val isBanned = bool("is_banned")
}