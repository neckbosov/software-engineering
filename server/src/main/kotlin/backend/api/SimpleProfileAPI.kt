package backend.api

import api.AbstractProfileAPI
import db.SimpleDatabase
import db.dao.*
import models.ProfileType
import models.Tag
import models.profile.*
import models.review.Review
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.math.BigDecimal

@Suppress("RemoveRedundantQualifierName")
class SimpleProfileAPI(val database: SimpleDatabase) : AbstractProfileAPI {
    init {
        transaction {
//            addLogger(StdOutSqlLogger)
            SchemaUtils.create(Profiles, Students, Instructors, Achievements, Jobs, Tags, ResearchWorks)
        }
        // ???
        /*
        transaction {
            Tags.deleteAll()
            Achievements.deleteAll()
            Instructors.deleteAll()
            Jobs.deleteAll()
            Profiles.deleteAll()
            ResearchWorks.deleteAll()
            Students.deleteAll()
        }
        */
    }

    private fun updateCommonProfile(profileId: Long, profile: UserProfile) {
        Profiles.update({ Profiles.id.eq(profileId) }) {
            it[Profiles.avatarUrl] = profile.avatarURL
            it[Profiles.firstName] = profile.name
            it[Profiles.lastName] = profile.surname
            it[Profiles.patronymic] = profile.patronymic
            it[Profiles.isActive] = profile.status == Status.ACTIVE
        }
        Achievements.deleteWhere {
            Achievements.profileId eq profileId
        }
        for (achievement: AchievementDescription in profile.achievements) {
            Achievements.insert {
                it[Achievements.profileId] = profileId
                it[Achievements.type] = achievement.type
                it[Achievements.name] = achievement.title
                it[Achievements.description] = achievement.description
                it[Achievements.date] = achievement.date
            }
        }
        Jobs.deleteWhere {
            Jobs.profileId eq profileId
        }
        for (job: JobDescription in profile.career) {
            Jobs.insert {
                it[Jobs.profileId] = profileId
                it[Jobs.fromDate] = job.period.first
                it[Jobs.toDate] = job.period.second
                it[Jobs.position] = job.position
                it[Jobs.place] = job.place
            }
        }
        Tags.deleteWhere {
            Tags.profileId eq profileId
        }
        for (tag: Tag in profile.interestsTags) {
            Tags.insert {
                it[Tags.profileId] = profileId
                it[Tags.tag] = tag
            }
        }
    }

    override suspend fun updateStudentProfile(id: Long, profile: StudentProfile) {
        return newSuspendedTransaction {

//            addLogger(StdOutSqlLogger)
            val profileId = id
            updateCommonProfile(profileId, profile)
            Students.update({ Students.profileId.eq(profileId) }) {
                it[Students.university] = profile.universityDescription.universityName
                it[Students.faculty] = profile.universityDescription.faculty
                it[Students.degree] = profile.universityDescription.grade
                it[Students.course] = profile.universityDescription.course
                it[Students.from] = profile.universityDescription.period.first
                it[Students.to] = profile.universityDescription.period.second
                it[Students.gpa] = profile.universityDescription.gpa?.let { it1 -> BigDecimal(it1.toDouble()) }
                it[Students.cvUrl] = profile.cvURL
            }
        }
    }

    override suspend fun getStudentProfile(id: Long): StudentProfile {
        return newSuspendedTransaction {

//            addLogger(StdOutSqlLogger)
            val studentProfile = Profiles.select { Profiles.id.eq(id) }.toList()[0]
            val studentAchievements = Achievements.select { Achievements.profileId.eq(id) }.map {
                AchievementDescription(
                    it[Achievements.type],
                    it[Achievements.name],
                    it[Achievements.description],
                    it[Achievements.date]
                )
            }
            val studentCareer = Jobs.select { Jobs.profileId.eq(id) }.map {
                JobDescription(
                    it[Jobs.place],
                    it[Jobs.position],
                    Pair(it[Jobs.fromDate], it[Jobs.toDate])
                )
            }
            val studentInterestingTags = Tags.select { Tags.profileId.eq(id) }.map {
                it[Tags.tag]
            }

            val studentData = Students.select { Students.profileId.eq(id) }.toList()[0]
            val universityData = UniversityDescription(
                studentData[Students.university],
                studentData[Students.faculty],
                studentData[Students.degree],
                studentData[Students.course],
                Pair(studentData[Students.from], studentData[Students.to]),
                studentData[Students.gpa]?.toFloat()
            )

            val reviews = Reviews.select { Reviews.userId.eq(id)}.map {
                Review(
                    it[Reviews.userId].value,
                    it[Reviews.reviewerId].value,
                    it[Reviews.date],
                    it[Reviews.body]
                )
            }

            StudentProfile(
                studentProfile[Profiles.email],
                studentProfile[Profiles.firstName],
                studentProfile[Profiles.lastName],
                studentProfile[Profiles.patronymic],
                studentProfile[Profiles.avatarUrl],
                studentCareer,
                studentAchievements,
                studentInterestingTags,
                if (studentProfile[Profiles.isActive]) {
                    Status.ACTIVE
                } else {
                    Status.NON_ACTIVE
                },
                reviews,
                universityData,
                studentData[Students.cvUrl]
            )
        }
    }

    override suspend fun updateInstructorProfile(id: Long, profile: InstructorProfile) {
        newSuspendedTransaction {
            addLogger(StdOutSqlLogger)
            val profileId = id
            updateCommonProfile(profileId, profile)
            val instructor = Instructors.select { Instructors.profileId.eq(profileId) }.toList()[0]
            val instructorId = instructor[Instructors.id]
            ResearchWorks.deleteWhere {
                ResearchWorks.instructorId eq instructorId
            }

            for (research: ResearchWorkDescription in profile.works) {
                ResearchWorks.insert {
                    it[ResearchWorks.instructorId] = instructorId
                    it[ResearchWorks.title] = research.name
                    it[ResearchWorks.description] = research.description
                    it[ResearchWorks.detailsURL] = research.detailsURL
                }
            }

            Instructors.update({ Instructors.profileId.eq(id) }) {
                it[Instructors.degree] = profile.degree
            }
        }

    }

    override suspend fun getInstructorProfile(id: Long): InstructorProfile {
        return newSuspendedTransaction {

//            addLogger(StdOutSqlLogger)
            val instructorProfile = Profiles.select { Profiles.id.eq(id) }.toList()[0]
            val achievements = Achievements.select { Achievements.profileId.eq(id) }.map {
                AchievementDescription(
                    it[Achievements.type],
                    it[Achievements.name],
                    it[Achievements.description],
                    it[Achievements.date]
                )
            }
            val career = Jobs.select { Jobs.profileId.eq(id) }.map {
                JobDescription(
                    it[Jobs.place],
                    it[Jobs.position],
                    Pair(it[Jobs.fromDate], it[Jobs.toDate])
                )
            }
            val instructorInterestingTags = Tags.select { Tags.profileId.eq(id) }.map {
                it[Tags.tag]
            }

            val instructor = Instructors.select { Students.profileId.eq(id) }.toList()[0]
            val researches = ResearchWorks.select { ResearchWorks.instructorId.eq(id) }.map {
                ResearchWorkDescription(
                    it[ResearchWorks.title],
                    it[ResearchWorks.description],
                    it[ResearchWorks.detailsURL]
                )
            }

            val reviews = Reviews.select { Reviews.userId.eq(id)}.map {
                Review(
                    it[Reviews.userId].value,
                    it[Reviews.reviewerId].value,
                    it[Reviews.date],
                    it[Reviews.body]
                )
            }

            InstructorProfile(
                instructorProfile[Profiles.email],
                instructorProfile[Profiles.firstName],
                instructorProfile[Profiles.lastName],
                instructorProfile[Profiles.patronymic],
                instructorProfile[Profiles.avatarUrl],
                career,
                achievements,
                instructorInterestingTags,
                if (instructorProfile[Profiles.isActive]) {
                    Status.ACTIVE
                } else {
                    Status.NON_ACTIVE
                },
                reviews,
                instructor[Instructors.degree],
                researches,
            )
        }

    }

    override suspend fun getIdByEmail(email: String): Long {
        return newSuspendedTransaction {

            addLogger(StdOutSqlLogger)
            val profile = Profiles.select {
                Profiles.email.eq(email)
            }.toList()[0]
            profile[Profiles.id].value
        }
    }

    override suspend fun getProfile(id: Long): UserProfile {
        return newSuspendedTransaction {

//            addLogger(StdOutSqlLogger)
            val profile = Profiles.select { Profiles.id.eq(id) }.toList()[0]
            val achievements = Achievements.select { Achievements.profileId.eq(id) }.map {
                AchievementDescription(
                    it[Achievements.type],
                    it[Achievements.name],
                    it[Achievements.description],
                    it[Achievements.date]
                )
            }
            val career = Jobs.select { Jobs.profileId.eq(id) }.map {
                JobDescription(
                    it[Jobs.place],
                    it[Jobs.position],
                    Pair(it[Jobs.fromDate], it[Jobs.toDate])
                )
            }
            val tags = Tags.select { Tags.profileId.eq(id) }.map {
                it[Tags.tag]
            }

            val reviews = Reviews.select { Reviews.userId.eq(id)}.map {
                Review(
                    it[Reviews.userId].value,
                    it[Reviews.reviewerId].value,
                    it[Reviews.date],
                    it[Reviews.body]
                )
            }

            when (profile[Profiles.profileType]) {
                ProfileType.Student -> {
                    val studentData = Students.select { Students.profileId.eq(id) }.toList()[0]
                    val universityData = UniversityDescription(
                        studentData[Students.university],
                        studentData[Students.faculty],
                        studentData[Students.degree],
                        studentData[Students.course],
                        Pair(studentData[Students.from], studentData[Students.to]),
                        studentData[Students.gpa]?.toFloat()
                    )
                    StudentProfile(
                        profile[Profiles.email],
                        profile[Profiles.firstName],
                        profile[Profiles.lastName],
                        profile[Profiles.patronymic],
                        profile[Profiles.avatarUrl],
                        career,
                        achievements,
                        tags,
                        if (profile[Profiles.isActive]) {
                            Status.ACTIVE
                        } else {
                            Status.NON_ACTIVE
                        },
                        reviews,
                        universityData,
                        studentData[Students.cvUrl]
                    )
                }
                ProfileType.Instructor -> {
                    val instructor = Instructors.select { Instructors.profileId.eq(id) }.toList()[0]
                    val researches = ResearchWorks.select { ResearchWorks.instructorId.eq(id) }.map {
                        ResearchWorkDescription(
                            it[ResearchWorks.title],
                            it[ResearchWorks.description],
                            it[ResearchWorks.detailsURL]
                        )
                    }
                    InstructorProfile(
                        profile[Profiles.email],
                        profile[Profiles.firstName],
                        profile[Profiles.lastName],
                        profile[Profiles.patronymic],
                        profile[Profiles.avatarUrl],
                        career,
                        achievements,
                        tags,
                        if (profile[Profiles.isActive]) {
                            Status.ACTIVE
                        } else {
                            Status.NON_ACTIVE
                        },
                        reviews,
                        instructor[Instructors.degree],
                        researches
                    )
                }
            }
        }

    }


}