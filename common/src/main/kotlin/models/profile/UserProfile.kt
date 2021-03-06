package models.profile

import kotlinx.serialization.Serializable
import models.Tags
import models.review.Review

@Serializable
sealed class UserProfile {
    abstract val email: String
    abstract val name: String
    abstract val surname: String
    abstract val patronymic: String?

    // TODO
    abstract val avatarURL: String?

    abstract val career: List<JobDescription>
    abstract val achievements: List<AchievementDescription>

    abstract val interestsTags: Tags

    abstract val status: Status

    abstract val reviews: List<Review>
}

@Serializable
data class InstructorProfile(
    override val email: String,
    override val name: String,
    override val surname: String,
    override val patronymic: String? = null,
    override val avatarURL: String? = null,
    override val career: List<JobDescription>,
    override val achievements: List<AchievementDescription>,
    override val interestsTags: Tags,
    override val status: Status,
    override val reviews: List<Review>,

    val degree: String,
    val works: List<ResearchWorkDescription>,
) : UserProfile()

@Serializable
data class StudentProfile(
    override val email: String,
    override val name: String,
    override val surname: String,
    override val patronymic: String? = null,
    override val avatarURL: String? = null,
    override val career: List<JobDescription>,
    override val achievements: List<AchievementDescription>,
    override val interestsTags: Tags,
    override val status: Status,
    override val reviews: List<Review>,

    val universityDescription: UniversityDescription,
    val cvURL: String? = null,
) : UserProfile()


