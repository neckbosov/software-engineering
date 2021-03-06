package ui.authorization

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.ktor.http.*
import kotlinx.coroutines.launch
import ui.SimpleAppInfo
import ui.profile.create.ProfileCreateState
import ui.profile.view.ProfileViewState
import ui.utils.openInBrowser
import java.awt.SystemColor.text
import java.lang.Exception
import java.net.URI

@Composable
@Preview
fun Authorization(appInfo: SimpleAppInfo) {
    val scope = rememberCoroutineScope()
    val errorMessage = remember { mutableStateOf<String?>(null) }

    MaterialTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar {
                Text("MeeTut", color = Color.White)
            }

            Column(
                modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "MeeTut",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h3
                )

                Spacer(Modifier.heightIn(20.dp))

                Text(
                    text = "MeeTut is a platform for students and professors, where you can find a professor/student for the thesis work",
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
                        scope.launch {
                            val authData = appInfo.client.loginViaGoogle()
                            val authToken = authData.token
                            openInBrowser(URI.create(authData.authURI))
                            scope.launch {
                                try {
                                    val jwt = appInfo.client.postLoginViaGoogle(authToken)
                                    appInfo.currentJwt = jwt
                                    appInfo.currentState.value = ProfileViewState(appInfo.currentId!!)
                                } catch (e: Exception) {
                                    errorMessage.value = e.message
                                }
                            }
                        }
                    }
                ) {
                    Image(
                        painter = painterResource("sign-in-google.png"),
                        "google sign in button",
                        modifier = Modifier.background(Color.Transparent)
                    )
                }

                errorMessage.value?.let {
                    Text(
                        it,
                        color = Color.Red
                    )
                }

                Spacer(Modifier.heightIn(20.dp))

                Text(
                    text = "Click here to register a new account!",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.clickable {
                        appInfo.currentState.value = ProfileCreateState()
                    }
                )
            }
        }
    }
}

