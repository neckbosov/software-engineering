package models.profile

import models.Tags

data class InstructorProfile(
    override val name: String,
    override val surname: String,
    override val patronymic: String? = null,
    override val avatarURL: String? = null,
    override val career: List<JobDescription>,
    override val achievements: List<AchievementDescription>,
    override val interestsTags: Tags,
    override val status: Status,

    val degree: String,
    val works: List<ResearchWorkDescription>,
) : UserProfile
