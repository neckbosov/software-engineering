package models.profile

import kotlinx.serialization.Serializable

@Serializable
enum class Status {
    ACTIVE,
    NON_ACTIVE;
}
