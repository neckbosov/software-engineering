package models.profile

import models.Tags

interface UserProfile {
    val name: String
    val surname: String
    val patronymic: String

    // TODO
    val avatarURL: String

    val career: List<JobDescription>
    val achievements: List<AchievementDescription>

    val interestsTags: Tags

    val status: Status
}
