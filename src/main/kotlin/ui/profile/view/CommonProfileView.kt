package ui.profile.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.Tags
import models.profile.*
import ui.utils.loadNetworkImage
import ui.utils.statusToString

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
fun EmailView(email: String, modifier: Modifier = Modifier) {
    Text(
        text = "Email: $email",
        style = TextStyle(
            fontSize = 15f.sp,
            fontWeight = FontWeight.SemiBold,
        ),
        modifier = modifier
    )
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
fun StatusView(status: Status, modifier: Modifier = Modifier) {
    Text(
        text = "[${statusToString(status)}]",
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
fun ImageView(avatarURL: String?, modifier: Modifier = Modifier) {
    val picture = if (avatarURL != null) loadNetworkImage(avatarURL) else null
    if (picture != null) {
        Image(
            bitmap = picture,
            contentDescription = "avatar",
            modifier = modifier.background(Color.Transparent).requiredSize(200.dp),
        )
    } else {
        Image(
            painter = painterResource("empty-avatar.jpg"),
            contentDescription = "avatar",
            modifier = modifier.background(Color.Transparent).requiredSize(200.dp),
        )
    }
}