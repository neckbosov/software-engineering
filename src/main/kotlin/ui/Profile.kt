package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.Tags
import models.profile.*

@Composable
@Preview
fun ProfileView(profile: UserProfile, modifier: Modifier = Modifier) {
    BoxWithVerticalScroll(modifier = modifier.fillMaxSize(1f)) {
        Column {
            Row {
                ImageView(profile.avatarURL)
                ProfileInfoView(profile, modifier = Modifier.padding(start = 10.dp))
            }
            AchievmentsView(profile.achievements, modifier = Modifier.padding(top = 20.dp))
            CarrierView(profile.career, modifier = Modifier.padding(top = 10.dp))
            AdditionalInfoView(profile, modifier = Modifier.padding(top = 10.dp))
        }
    }
}

@Composable
@Preview
fun BoxWithVerticalScroll(modifier: Modifier, content: @Composable BoxScope.() -> Unit ) {
    val stateVertical = rememberScrollState(0)
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 10.dp, end = 10.dp)
    ) {
        Box(
            modifier = modifier
                .fillMaxSize(1f)
                .verticalScroll(stateVertical)
        ) {
            content()
        }
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd)
                .fillMaxHeight(),
            adapter = rememberScrollbarAdapter(stateVertical)
        )
    }
}

@Composable
@Preview
fun ProfileInfoView(profile: UserProfile, modifier: Modifier = Modifier) {
    when (profile) {
        is StudentProfile -> StudentProfileInfoView(profile, modifier)
        is InstructorProfile -> InstructorProfileInfoView(profile, modifier)
    }
}

@Composable
@Preview
fun StudentProfileInfoView(profile: StudentProfile, modifier: Modifier = Modifier) {
    Column(modifier) {
        Row {
            FullNameView(
                fullName = "${profile.surname} ${profile.name} ${profile.patronymic}",
                modifier = Modifier
            )
            Spacer(modifier = Modifier.widthIn(min = 5.dp, max = 10.dp))
            StatusView(profile.status)
        }
        Spacer(modifier = Modifier.heightIn(min = 10.dp, max = 20.dp))
        val universityInfo = profile.universityDescription
        Text(
            text = "Student in ${universityInfo.universityName}, ${universityInfo.faculty}",
            style = TextStyle(
                fontSize = 15f.sp,
                fontWeight = FontWeight.SemiBold,
            )
        )
        Spacer(modifier = Modifier.heightIn(min = 10.dp, max = 20.dp))
        Text(
            text = "Grade: ${universityInfo.grade}",
            style = TextStyle(
                fontSize = 15f.sp,
                fontWeight = FontWeight.SemiBold,
            )
        )
        Spacer(modifier = Modifier.heightIn(min = 10.dp, max = 20.dp))
        Text(
            text = "GPA: ${universityInfo.gpa}",
            style = TextStyle(
                fontSize = 15f.sp,
                fontWeight = FontWeight.SemiBold,
            )
        )
        Spacer(modifier = Modifier.heightIn(min = 10.dp, max = 20.dp))
        TagsView(profile.interestsTags)
    }
}

@Composable
@Preview
fun InstructorProfileInfoView(profile: InstructorProfile, modifier: Modifier = Modifier) {
    Column(modifier) {
        Row {
            FullNameView(
                fullName = "${profile.surname} ${profile.name} ${profile.patronymic}",
                modifier = Modifier
            )
            Spacer(modifier = Modifier.widthIn(min = 5.dp, max = 10.dp))
            StatusView(profile.status)
        }
        Spacer(modifier = Modifier.heightIn(min = 10.dp, max = 20.dp))
        Text(
            text = "Tutor in ${profile.degree}",
            style = TextStyle(
                fontSize = 15f.sp,
                fontWeight = FontWeight.SemiBold,
            )
        )
        Spacer(modifier = Modifier.heightIn(min = 10.dp, max = 20.dp))
        TagsView(profile.interestsTags)
    }
}

@Composable
@Preview
fun AchievmentsView(achievements : List<AchievementDescription>, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "Achievements:",
            style = TextStyle(
                fontSize = 25f.sp,
                fontWeight = FontWeight.SemiBold,
            ),
        )
        Spacer(modifier = Modifier.heightIn(min = 10.dp, max = 20.dp))
        achievements.forEachIndexed { i, achievement ->
            Row {
                Text(
                    text = "$i)",
                    fontSize = 20f.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.widthIn(min = 5.dp, max = 10.dp))
                Text(
                    text = achievement.title,
                    fontSize = 20f.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.widthIn(min = 5.dp, max = 10.dp))
                Text(
                    text = "(${achievement.date})",
                    fontSize = 20f.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.widthIn(min = 5.dp, max = 10.dp))
                Text(
                    text = "[${achievement.type}]",
                    fontSize = 20f.sp,
                    color = Color.DarkGray
                )
            }
            Spacer(modifier = Modifier.heightIn(min = 10.dp, max = 20.dp))
            Text(
                text = achievement.description
            )
            Spacer(modifier = Modifier.heightIn(min = 10.dp, max = 20.dp))
        }
    }
}

@Composable
@Preview
fun CarrierView(jobs: List<JobDescription>, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "Carrier:",
            style = TextStyle(
                fontSize = 25f.sp,
                fontWeight = FontWeight.SemiBold,
            ),
        )
        Spacer(modifier = Modifier.heightIn(min = 10.dp, max = 20.dp))
        jobs.forEachIndexed { i, job ->
            Row {
                Text(
                    text = "$i)",
                    fontSize = 20f.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.widthIn(min = 5.dp, max = 10.dp))
                Text(
                    text = "${job.position} at ${job.place}",
                    fontSize = 20f.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.widthIn(min = 5.dp, max = 10.dp))
                Text(
                    text = "(${job.period.first} - ${job.period.second})",
                    fontSize = 20f.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )
            }
            Spacer(modifier = Modifier.heightIn(min = 10.dp, max = 20.dp))
        }
    }
}

@Composable
@Preview
fun TagsView(tags: Tags, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Text(
            text = "Tags: ",
            style = TextStyle(
                fontSize = 15f.sp,
                fontWeight = FontWeight.SemiBold,
            ),
            modifier = Modifier
        )
        Text(tags.joinToString())
    }
}

@Composable
@Preview
fun AdditionalInfoView(profile: UserProfile, modifier: Modifier = Modifier) {
    when (profile) {
        is StudentProfile -> StudentAdditionalInfoView(profile, modifier)
        is InstructorProfile -> InstructorAdditionalInfoView(profile, modifier)
    }
}

@Composable
@Preview
fun StudentAdditionalInfoView(profile: StudentProfile, modifier: Modifier = Modifier) {
    Row(modifier) {
        Text(
            text = "CV URL: ",
            style = TextStyle(
                fontSize = 20f.sp,
                fontWeight = FontWeight.SemiBold,
            ),
        )
        Text(
            text = profile.cvURL ?: "Not loaded",
            fontSize = 20f.sp
        )
    }
}

@Composable
@Preview
fun InstructorAdditionalInfoView(profile: InstructorProfile, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "Works:",
            style = TextStyle(
                fontSize = 25f.sp,
                fontWeight = FontWeight.SemiBold,
            ),
        )
        Spacer(modifier = Modifier.heightIn(min = 10.dp, max = 20.dp))
        profile.works.forEachIndexed { i, work ->
            Row {
                Text(
                    text = "$i)",
                    fontSize = 20f.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.widthIn(min = 5.dp, max = 10.dp))
                Text(
                    text = work.name,
                    fontSize = 20f.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Spacer(modifier = Modifier.heightIn(min = 10.dp, max = 20.dp))
            Text(
                text = work.description
            )
            Spacer(modifier = Modifier.heightIn(min = 10.dp, max = 20.dp))
            Text(
                text = "Details: ${work.detailsURL}",
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(modifier = Modifier.heightIn(min = 10.dp, max = 20.dp))
        }
    }
}

@Composable
@Preview
fun StatusView(status: Status, modifier: Modifier = Modifier) {
    val statusText = when (status) {
        Status.ACTIVE -> "Active"
        Status.NON_ACTIVE -> "Non active"
    }
    Text(
        text = "[$statusText]",
        color = Color.DarkGray,
        modifier = modifier
    )
}

@Composable
@Preview
fun FullNameView(fullName: String, modifier: Modifier = Modifier) {
    Text(
        text = fullName,
        style = TextStyle(
            color = Color.Black,
            fontSize = 25f.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.SemiBold
        ),
        modifier = modifier
    )
}

@Composable
@Preview
fun ImageView(avatarURL: String, modifier: Modifier = Modifier) {
    Image(
        bitmap = loadNetworkImage(avatarURL),
        contentDescription = "avatar",
        modifier = modifier.background(Color.Transparent).requiredSize(200.dp),
    )
}