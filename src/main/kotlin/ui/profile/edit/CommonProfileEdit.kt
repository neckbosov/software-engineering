package ui.profile.edit

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.Tag
import models.Tags
import models.profile.*
import ui.profile.edit.models.AchievementDescriptionEdit
import ui.profile.edit.models.JobDescriptionEdit
import ui.profile.edit.models.UserProfileInfoEdit
import ui.profile.view.ImageView
import ui.utils.loadNetworkImage
import ui.utils.statusToString
import kotlin.math.sin

@Composable
@Preview
fun NameEdit(profile: UserProfileInfoEdit, modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.spacedBy(5.dp), modifier = modifier) {
        Row {
            Text(
                text = "Account:",
                style = TextStyle(
                    fontSize = 25f.sp,
                    fontWeight = FontWeight.SemiBold,
                ),
            )
            StatusEdit(profile.status)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            OutlinedTextField(
                value = profile.surname.value,
                onValueChange = { profile.surname.value = it },
                label = { Text("Surname") },
                singleLine = true,
                modifier = Modifier.width(150.dp)
            )
            OutlinedTextField(
                value = profile.name.value,
                onValueChange = { profile.name.value = it },
                label = { Text("Name") },
                singleLine = true,
                modifier = Modifier.width(150.dp)
            )
            OutlinedTextField(
                value = profile.patronymic.value,
                onValueChange = { profile.patronymic.value = it },
                label = { Text("Patronymic") },
                singleLine = true,
                modifier = Modifier.width(150.dp)
            )
        }
    }
}

@Composable
@Preview
fun AchievmentsEdit(achievements: SnapshotStateList<AchievementDescriptionEdit>, modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = modifier) {
        Row {
            Text(
                text = "Achievements:",
                style = TextStyle(
                    fontSize = 25f.sp,
                    fontWeight = FontWeight.SemiBold,
                ),
            )
            IconButton(
                onClick = {
                    achievements += AchievementDescriptionEdit(
                        AchievementDescription(
                            type = "",
                            title = "",
                            description = "",
                            date = ""
                        )
                    )
                }
            ) {
                Icon(Icons.Filled.Add, "add achievement")
            }
        }
        var isClicked = false
        for (i in 0 until achievements.size) {
            val achievement = achievements[i]
            Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(
                    text = "$i)",
                    fontSize = 20f.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                OutlinedTextField(
                    value = achievement.title.value,
                    onValueChange = { achievement.title.value = it },
                    label = { Text("Title") },
                    singleLine = true,
                    modifier = Modifier.width(200.dp)
                )
                OutlinedTextField(
                    value = achievement.date.value,
                    onValueChange = { achievement.date.value = it },
                    label = { Text("Date") },
                    singleLine = true,
                    modifier = Modifier.width(200.dp)
                )
                OutlinedTextField(
                    value = achievement.type.value,
                    onValueChange = { achievement.type.value = it },
                    label = { Text("Type") },
                    singleLine = true,
                    modifier = Modifier.width(200.dp)
                )
                IconButton(
                    onClick = {
                        achievements.removeAt(i)
                        isClicked = true
                    }
                ) {
                    Icon(Icons.Filled.Close, "delete achievement")
                }
            }
            OutlinedTextField(
                value = achievement.description.value,
                onValueChange = { achievement.description.value = it },
                label = { Text("Description") },
                modifier = Modifier.width(500.dp)
            )
            if (isClicked) break
        }
    }
}

@Composable
@Preview
fun CarrierEdit(jobs: SnapshotStateList<JobDescriptionEdit>, modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = modifier) {
        Row {
            Text(
                text = "Carrier:",
                style = TextStyle(
                    fontSize = 25f.sp,
                    fontWeight = FontWeight.SemiBold,
                ),
            )
            IconButton(
                onClick = {
                    jobs += JobDescriptionEdit(
                        JobDescription(
                            place = "",
                            position = "",
                            period = Pair("", "")
                        )
                    )
                }
            ) {
                Icon(Icons.Filled.Add, "add job")
            }
        }
        var isClicked = false
        for (i in 0 until jobs.size) {
            val job = jobs[i]
            Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(
                    text = "$i)",
                    fontSize = 20f.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                OutlinedTextField(
                    value = job.position.value,
                    onValueChange = { job.position.value = it },
                    label = { Text("Position") },
                    singleLine = true,
                    modifier = Modifier.width(200.dp)
                )
                OutlinedTextField(
                    value = job.place.value,
                    onValueChange = { job.place.value = it },
                    label = { Text("Place") },
                    singleLine = true,
                    modifier = Modifier.width(150.dp)
                )
                OutlinedTextField(
                    value = job.period1.value,
                    onValueChange = { job.period1.value = it },
                    label = { Text("From") },
                    singleLine = true,
                    modifier = Modifier.width(150.dp)
                )
                OutlinedTextField(
                    value = job.period2.value,
                    onValueChange = { job.period2.value = it },
                    label = { Text("To") },
                    singleLine = true,
                    modifier = Modifier.width(150.dp)
                )
                IconButton(
                    onClick = {
                        jobs.removeAt(i)
                        isClicked = true
                    }
                ) {
                    Icon(Icons.Filled.Close, "delete job")
                }
            }
            if (isClicked) break
        }
    }
}

@Composable
@Preview
fun TagsEdit(tags: SnapshotStateList<TextFieldValue>, modifier: Modifier = Modifier) {
    Row(horizontalArrangement = Arrangement.spacedBy(1.dp), modifier = modifier) {
        Text(
            text = "Tags:",
            style = TextStyle(
                fontSize = 25f.sp,
                fontWeight = FontWeight.SemiBold,
            ),
        )
        Spacer(modifier = Modifier.width(4.dp))
        var isClicked = false
        for (i in 0 until tags.size) {
            OutlinedTextField(
                value = tags[i],
                onValueChange = { tags[i] = it },
                label = { Text("Tag $i") },
                modifier = Modifier.width(100.dp),
                singleLine = true,
            )
            IconButton(
                onClick = {
                    tags.removeAt(i)
                    isClicked = true
                }
            ) {
                Icon(Icons.Filled.Close, "delete tag")
            }
            if (isClicked) break
            Spacer(modifier = Modifier.width(4.dp))
        }
        IconButton(
            onClick = {
                tags += TextFieldValue("")
            }
        ) {
            Icon(Icons.Filled.Add, "add tag")
        }
    }
}

@Composable
@Preview
fun StatusEdit(status: MutableState<Status>, modifier: Modifier = Modifier) {
    val expanded = remember { mutableStateOf(false) }
    TextButton(
        onClick = { expanded.value = true },
        modifier = modifier
    ) {
        Text(statusToString(status.value))
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
        ) {
            Status.values().forEach {
                DropdownMenuItem(
                    onClick = {
                        status.value = it
                    }
                ) {
                    Text(statusToString(it))
                }
                Divider()
            }
        }
    }
}

@Composable
@Preview
fun ImageEdit(avatarURL: MutableState<TextFieldValue>, modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        ImageView(avatarURL.value.text)
        OutlinedTextField(
            value = avatarURL.value,
            onValueChange = { avatarURL.value = it },
            label = { Text("Image URL") },
            singleLine = true,
            modifier = Modifier.width(200.dp)
        )
    }
}