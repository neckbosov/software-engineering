package ui.profile.create

import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import dao.ProfileType
import ui.SimpleAppInfo
import ui.profile.view.ProfileViewState
import ui.utils.openInBrowser

@Composable
@Preview
fun ProfileCreate(appInfo: SimpleAppInfo) {
    val profileType = remember { mutableStateOf(ProfileType.Student) }
    DesktopMaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Добро пожаловать в [Name]!",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h6
            )

            Spacer(Modifier.heightIn(20.dp))

            Text("Кто Вы?")
            Column(horizontalAlignment = Alignment.Start) {
                Row(
                    Modifier.padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (profileType.value == ProfileType.Student),
                        onClick = { profileType.value = ProfileType.Student }
                    )

                    val annotatedString = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(fontWeight = FontWeight.Bold)
                        ) { append("  Студент  ") }
                    }

                    ClickableText(
                        text = annotatedString,
                        onClick = {
                            profileType.value = ProfileType.Student
                        }
                    )
                }

                Row(
                    Modifier.padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (profileType.value == ProfileType.Instructor),
                        onClick = { profileType.value = ProfileType.Instructor }
                    )

                    val annotatedString = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(fontWeight = FontWeight.Bold)
                        ) { append("  Преподаватель  ") }
                    }

                    ClickableText(
                        text = annotatedString,
                        onClick = {
                            profileType.value = ProfileType.Instructor
                        }
                    )
                }
            }

            Spacer(Modifier.heightIn(20.dp))

            Button(
                onClick = {
                    /* TODO create profile backend methods */
                    //appInfo.currentState.value = ProfileViewState()
                }
            ) {
                Text("Создать профиль!")
            }
        }
    }
}