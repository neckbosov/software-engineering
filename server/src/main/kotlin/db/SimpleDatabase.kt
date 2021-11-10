package db

import models.Tag

interface SimpleDatabase {
    fun getStudentsIDByTag(tags: List<Tag>): List<Long>

    fun getInstructorsIDByTag(tags: List<Tag>): List<Long>
}
