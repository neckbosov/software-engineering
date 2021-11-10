package db

import models.Tag

interface SimpleDatabase {
    suspend fun getStudentsIDByTag(tags: List<Tag>): List<Long>

    suspend fun getInstructorsIDByTag(tags: List<Tag>): List<Long>
}
