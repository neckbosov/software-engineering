package backend.storage

import backend.api.authaccess.AuthFlags
import db.dao.Authorization
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction


object AuthorizationStorage {
    init {
        transaction {
            SchemaUtils.create(Authorization)
        }
    }

    fun getFlags(profileId: Long): AuthFlags {
        return transaction {
            val data = Authorization.select { Authorization.profileId eq profileId }.firstOrNull()
            if (data == null) {
                Authorization.insert {
                    it[Authorization.profileId] = profileId
                    it[Authorization.isAdmin] = false
                    it[Authorization.isModerator] = false
                    it[Authorization.isBanned] = false
                }
                AuthFlags(profileId, false, false, false)
            } else {
                AuthFlags(
                    profileId,
                    data[Authorization.isAdmin],
                    data[Authorization.isModerator],
                    data[Authorization.isBanned]
                )
            }
        }
    }
}