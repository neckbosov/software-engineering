package ui.authorization

import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import auth.GoogleApi
import auth.GoogleAppCredentials
import auth.GoogleOAuthHandler
import ui.DummyAppInfo
import ui.profile.view.ProfileViewState
import ui.utils.loadNetworkImage
import ui.utils.openInBrowser

@Composable
@Preview
fun Authorization(appInfo: DummyAppInfo) {
    val userInfoText = remember { mutableStateOf("") }
    val userPictureUrl = remember { mutableStateOf("") }
    val oauth =
        GoogleOAuthHandler(GoogleAppCredentials.fromFile(".secrets/google-app-desktop.txt")) { creds ->
            val userinfo = GoogleApi(creds).userInfo()
            println(userinfo)
            userInfoText.value = userinfo.toString()
            userPictureUrl.value = userinfo.avatarUrl
            appInfo.currentId = appInfo.backend.getIdByEmail(userinfo.email)
            appInfo.currentState.value = ProfileViewState()
        }
    DesktopMaterialTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar {
                Text("Logo")
            }

            Column(
                modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "[Name]",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h3
                )

                Spacer(Modifier.heightIn(20.dp))

                Text(
                    text = "[Name] is a platform for students and professors, where you can find a professor/student for the thesis work",
                    color = Color.Gray
                )

                Spacer(Modifier.heightIn(100.dp))

                Text(
                    text = "All you need to start is a Google account!",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h6
                )
                Box(
                    modifier = Modifier.clickable {
                        openInBrowser(oauth.oAuthURI)
                    }
                ) {
                    Image(
                        painter = painterResource("sign-in-google.png"),
                        "google sign in button",
                        modifier = Modifier.background(Color.Transparent)
                    )
                }

                Spacer(Modifier.heightIn(20.dp))

                Text(userInfoText.value, style = MaterialTheme.typography.h3)
                Image(
                    bitmap = if (userPictureUrl.value.isEmpty()) {
                        ImageBitmap(1, 1)
                    } else {
                        loadNetworkImage(userPictureUrl.value) ?: ImageBitmap(1, 1)
                    },
                    "avatar",
                    modifier = Modifier.background(Color.Transparent)
                )
            }
        }
    }
}

