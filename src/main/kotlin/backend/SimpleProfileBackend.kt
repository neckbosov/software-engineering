package backend

import dao.*
import models.AbstractProfileBackend
import models.Tag
import models.profile.InstructorProfile
import models.profile.StudentProfile
import models.profile.*
import org.jetbrains.exposed.sql.*
import java.math.BigDecimal

class SimpleProfileBackend : AbstractProfileBackend {

    override fun postStudentProfile(id: Long, profile: StudentProfile) {
        val profileId = Profiles.insertAndGetId {
            it[Profiles.avatarUrl] = profile.avatarURL
            it[Profiles.firstName] = profile.name
            it[Profiles.lastName] = profile.surname
            it[Profiles.patronymic] = profile.patronymic
            it[Profiles.isActive] = profile.status == Status.ACTIVE
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

        for (job: JobDescription in profile.career) {
            Jobs.insert {
                it[Jobs.profileId] = profileId
                it[Jobs.fromDate] = job.period.first
                it[Jobs.toDate] = job.period.second
                it[Jobs.position] = job.position
                it[Jobs.place] = job.place
            }
        }

        for (tag: Tag in profile.interestsTags) {
            Tags.insert {
                it[Tags.profileId] = profileId
                it[Tags.tag] = tag
            }
        }

        Students.insert {
            it[Students.profileId] = profileId
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

    override fun getStudentProfile(id: Long): StudentProfile {
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

        return StudentProfile(
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
            universityData,
            studentData[Students.cvUrl]
        )
    }

    override fun postInstructorProfile(id: Long, profile: InstructorProfile) {
        val profileId = Profiles.insertAndGetId {
            it[Profiles.avatarUrl] = profile.avatarURL
            it[Profiles.firstName] = profile.name
            it[Profiles.lastName] = profile.surname
            it[Profiles.patronymic] = profile.patronymic
            it[Profiles.isActive] = profile.status == Status.ACTIVE
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

        for (job: JobDescription in profile.career) {
            Jobs.insert {
                it[Jobs.profileId] = profileId
                it[Jobs.fromDate] = job.period.first
                it[Jobs.toDate] = job.period.second
                it[Jobs.position] = job.position
                it[Jobs.place] = job.place
            }
        }

        for (tag: Tag in profile.interestsTags) {
            Tags.insert {
                it[Tags.profileId] = profileId
                it[Tags.tag] = tag
            }
        }

        for (research: ResearchWorkDescription in profile.works) {
            ResearchWorks.insert {
                it[ResearchWorks.instructorId] = profileId
                it[ResearchWorks.title] = research.name
                it[ResearchWorks.description] = research.description
                it[ResearchWorks.detailsURL] = research.detailsURL
            }
        }

        Instructors.insert {
            it[Instructors.profileId] = profileId
            it[Instructors.degree] = profile.degree
        }
    }

    override fun getInstructorProfile(id: Long): InstructorProfile {
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

        return InstructorProfile(
            profile[Profiles.firstName],
            profile[Profiles.lastName],
            profile[Profiles.patronymic],
            profile[Profiles.avatarUrl],
            career,
            achievements,
            instructorInterestingTags,
            if (profile[Profiles.isActive]) {
                Status.ACTIVE
            } else {
                Status.NON_ACTIVE
            },
            instructor[Instructors.degree],
            researches
        )
    }
}