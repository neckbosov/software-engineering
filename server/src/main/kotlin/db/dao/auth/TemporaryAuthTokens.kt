package db.dao.auth

import models.ProfileType
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.timestamp
import java.sql.Timestamp

object TemporaryAuthTokens : IntIdTable() {
    val temporaryToken = text("temporary_token").uniqueIndex()
    val accessToken = text("access_token").nullable()
    val refreshToken = text("refresh_token").nullable()
    val expiresAt = timestamp("expiresAt").nullable()  // Google token expires
    val profileType = enumeration("profile_type", ProfileType::class)
    val timestamp = timestamp("timestamp")  // Our token expires
}