package ui.profile.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.profile.UserProfile

@Composable
@Preview
fun ReviewsView(profile: UserProfile, modifier: Modifier = Modifier) {
    val review = remember { mutableStateOf("") }
    Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = modifier) {
        OutlinedTextField(
            value = review.value,
            onValueChange = { review.value = it },
            placeholder = { Text("Please write an informative review") }
        )
        Text(
            text = "Reviews:",
            style = TextStyle(
                fontSize = 25f.sp,
                fontWeight = FontWeight.SemiBold,
            ),
        )
        profile.reviews.forEachIndexed { index, review ->  }
    }
}