package db.dao.auth

import db.dao.Profiles
import org.jetbrains.exposed.dao.id.IntIdTable

object AppId2PersonId : IntIdTable() {
    val appId = text("app_id")
    val profileId = long("profile_id")
        .uniqueIndex()
        .references(Profiles.id)
}