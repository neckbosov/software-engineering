package ui

import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun Authorization() {
    DesktopMaterialTheme {
        TopAppBar {
            Text("Logo")
        }
        Column(
            modifier = Modifier.fillMaxSize(),
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
                modifier = Modifier.clickable { }
            ) {
                Image(
                    painter = painterResource("sign-in-google.png"),
                    "google sign in button",
                    modifier = Modifier.background(Color.Transparent)
                )
            }
        }
    }
}