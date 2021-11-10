package ui.profile.edit

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import models.profile.ResearchWorkDescription
import ui.SimpleAppInfo
import ui.profile.edit.models.ResearchWorkDescriptionEdit
import ui.profile.edit.models.TMPInstructorProfileEdit
import ui.profile.view.ProfileViewState
import ui.utils.BoxWithVerticalScroll

@Composable
@Preview
fun InstructorProfileEdit(appInfo: SimpleAppInfo, profile: TMPInstructorProfileEdit, modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    BoxWithVerticalScroll(modifier = modifier.fillMaxSize(1f)) {
        Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
            Box(modifier = Modifier.fillMaxWidth(1f)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    ImageEdit(profile.avatarURL)
                    InstructorProfileInfoEdit(profile)
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    IconButton(
                        onClick = {
                            scope.launch {
                                appInfo.client.updateInstructorProfile(
                                    appInfo.currentId!!,
                                    profile.toInstructorProfile()
                                )
                                appInfo.currentState.value = ProfileViewState()
                            }
                        }
                    ) {
                        Icon(Icons.Filled.Done, "save")
                    }
                    IconButton(
                        onClick = { appInfo.currentState.value = ProfileViewState() }
                    ) {
                        Icon(Icons.Filled.Cancel, "close")
                    }
                }
            }
            TagsEdit(profile.interestsTags)
            AchievmentsEdit(profile.achievements)
            CarrierEdit(profile.career)
            WorksEdit(profile.works)
        }
    }
}

@Composable
@Preview
fun InstructorProfileInfoEdit(profile: TMPInstructorProfileEdit, modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = modifier.width(500.dp)) {
        NameEdit(profile)
        OutlinedTextField(
            value = profile.degree.value,
            onValueChange = { profile.degree.value = it },
            label = { Text("Degree") },
            singleLine = true,
            modifier = Modifier
        )
    }
}

@Composable
@Preview
fun WorksEdit(works: SnapshotStateList<ResearchWorkDescriptionEdit>, modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = modifier) {
        Row {
            Text(
                text = "Works:",
                style = TextStyle(
                    fontSize = 25f.sp,
                    fontWeight = FontWeight.SemiBold,
                ),
            )
            IconButton(
                onClick = {
                    works += ResearchWorkDescriptionEdit(
                        ResearchWorkDescription(
                            name = "",
                            description = "",
                            detailsURL = "",
                        )
                    )
                }
            ) {
                Icon(Icons.Filled.Add, "add work")
            }
        }
        var isClicked = false
        for (i in 0 until works.size) {
            val work = works[i]
            Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(
                    text = "$i)",
                    fontSize = 20f.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                OutlinedTextField(
                    value = work.name.value,
                    onValueChange = { work.name.value = it },
                    label = { Text("Name") },
                    singleLine = true,
                    modifier = Modifier.width(200.dp)
                )
                IconButton(
                    onClick = {
                        works.removeAt(i)
                        isClicked = true
                    }
                ) {
                    Icon(Icons.Filled.Close, "delete work")
                }
            }
            OutlinedTextField(
                value = work.description.value,
                onValueChange = { work.description.value = it },
                label = { Text("Description") },
                modifier = Modifier.width(500.dp)
            )
            OutlinedTextField(
                value = work.detailsURL.value,
                onValueChange = { work.detailsURL.value = it },
                label = { Text("Details URL") },
                singleLine = true,
                modifier = Modifier.width(500.dp)
            )
            if (isClicked) break
        }
    }
}