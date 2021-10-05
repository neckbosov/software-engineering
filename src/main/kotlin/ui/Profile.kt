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
import io.ktor.auth.*
import models.Tags
import models.profile.*

@Composable
@Preview
fun ProfileView(profile: UserProfile, modifier: Modifier = Modifier) {
    BoxWithVerticalScroll(modifier = modifier.fillMaxSize(1f)) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                ImageView(profile.avatarURL)
                ProfileInfoView(profile)
            }
            Spacer(modifier = Modifier.height(5.dp))
            AchievmentsView(profile.achievements)
            CarrierView(profile.career)
            AdditionalInfoView(profile)
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
fun NameView(profile: UserProfile, modifier: Modifier = Modifier) {
    Row(horizontalArrangement = Arrangement.spacedBy(5.dp), modifier = modifier) {
        FullNameView(
            fullName = "${profile.surname} ${profile.name} ${profile.patronymic}",
            modifier = Modifier
        )
        StatusView(profile.status)
    }
}

@Composable
@Preview
fun StudentProfileInfoView(profile: StudentProfile, modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = modifier) {
        NameView(profile)
        val universityInfo = profile.universityDescription
        Text(
            text = "Student in ${universityInfo.universityName}, ${universityInfo.faculty}",
            style = TextStyle(
                fontSize = 15f.sp,
                fontWeight = FontWeight.SemiBold,
            )
        )
        Text(
            text = "Grade: ${universityInfo.grade}",
            style = TextStyle(
                fontSize = 15f.sp,
                fontWeight = FontWeight.SemiBold,
            )
        )
        Text(
            text = "GPA: ${universityInfo.gpa}",
            style = TextStyle(
                fontSize = 15f.sp,
                fontWeight = FontWeight.SemiBold,
            )
        )
        TagsView(profile.interestsTags)
    }
}

@Composable
@Preview
fun InstructorProfileInfoView(profile: InstructorProfile, modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = modifier) {
        NameView(profile)
        Text(
            text = "Tutor in ${profile.degree}",
            style = TextStyle(
                fontSize = 15f.sp,
                fontWeight = FontWeight.SemiBold,
            )
        )
        TagsView(profile.interestsTags)
    }
}

@Composable
@Preview
fun AchievmentsView(achievements : List<AchievementDescription>, modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = modifier) {
        Text(
            text = "Achievements:",
            style = TextStyle(
                fontSize = 25f.sp,
                fontWeight = FontWeight.SemiBold,
            ),
        )
        achievements.forEachIndexed { i, achievement ->
            Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(
                    text = "$i)",
                    fontSize = 20f.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = achievement.title,
                    fontSize = 20f.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = "(${achievement.date})",
                    fontSize = 20f.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )
                Text(
                    text = "[${achievement.type}]",
                    fontSize = 20f.sp,
                    color = Color.DarkGray
                )
            }
            Text(text = achievement.description)
        }
    }
}

@Composable
@Preview
fun CarrierView(jobs: List<JobDescription>, modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = modifier) {
        Text(
            text = "Carrier:",
            style = TextStyle(
                fontSize = 25f.sp,
                fontWeight = FontWeight.SemiBold,
            ),
        )
        jobs.forEachIndexed { i, job ->
            Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(
                    text = "$i)",
                    fontSize = 20f.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = "${job.position} at ${job.place}",
                    fontSize = 20f.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = "(${job.period.first} - ${job.period.second})",
                    fontSize = 20f.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )
            }
        }
    }
}

@Composable
@Preview
fun TagsView(tags: Tags, modifier: Modifier = Modifier) {
    Row(horizontalArrangement = Arrangement.spacedBy(5.dp), modifier = modifier) {
        Text(
            text = "Tags: ",
            style = TextStyle(
                fontSize = 15f.sp,
                fontWeight = FontWeight.SemiBold,
            ),
            modifier = Modifier
        )
        tags.forEach {
            Text(it)
        }
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
    Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = modifier) {
        Text(
            text = "Works:",
            style = TextStyle(
                fontSize = 25f.sp,
                fontWeight = FontWeight.SemiBold,
            ),
        )
        profile.works.forEachIndexed { i, work ->
            Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(
                    text = "$i)",
                    fontSize = 20f.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = work.name,
                    fontSize = 20f.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Text(text = work.description)
            Text(
                text = "Details: ${work.detailsURL}",
                fontWeight = FontWeight.SemiBold,
            )
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