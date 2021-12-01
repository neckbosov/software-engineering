package backend

import models.auth.AbstractEmailAuthStorage

class BasicEmailAuthStorage : AbstractEmailAuthStorage {
    val storage = mutableMapOf<String, Pair<String, Long>>() // email -> (password, profile_id)

    override fun set(email: String, password: String, profileId: Long) {
        storage[email] = Pair(password, profileId)
    }

    override fun get(email: String): Pair<String, Long>? {
        return storage[email]
    }
}