package models.profile

import models.Tags

data class StudentProfile(
    override val name: String,
    override val surname: String,
    override val patronymic: String? = null,
    override val avatarURL: String? = null,
    override val career: List<JobDescription>,
    override val achievements: List<AchievementDescription>,
    override val interestsTags: Tags,
    override val status: Status,

    val universityDescription: UniversityDescription,
    val cvURL: String? = null,
) : UserProfile
