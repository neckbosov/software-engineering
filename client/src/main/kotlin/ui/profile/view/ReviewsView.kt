package ui.profile.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import models.profile.UserProfile
import models.review.Review
import ui.SimpleAppInfo
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
@Preview
fun ReviewsView(appInfo: SimpleAppInfo, profileId: Long, profile: UserProfile, modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()
    val review = remember { mutableStateOf("") }
    val reviews = remember { mutableStateOf<List<Review>>(emptyList()) }
    coroutineScope.launch {
        reviews.value = appInfo.client.getReviews(profileId)
    }
    Row {
        Spacer(modifier=Modifier.width(20.dp))
        Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = modifier) {
            Column(
                horizontalAlignment = Alignment.End
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(0.4f).padding(5.dp),
                    value = review.value,
                    onValueChange = { review.value = it },
                    placeholder = { Text("Please write an informative review") }
                )
                Spacer(modifier = Modifier.width(15.dp))
                Button({
                    coroutineScope.launch {
                        val date = SimpleDateFormat("dd/M/yyyy hh:mm").format(Date())
                        val r = Review(profileId, appInfo.currentId!!, date, review.value)
                        appInfo.client.postReview(r)
                        review.value = ""
                        reviews.value += r
                    }
                }) { Text("add") }
            }
            Text(
                text = "Reviews:",
                style = TextStyle(
                    fontSize = 25f.sp,
                    fontWeight = FontWeight.SemiBold,
                ),
            )
            LazyColumn {
                items(reviews.value.size) { index: Int ->
                    ReviewCard(appInfo, reviews.value[index])
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

@Composable
@Preview
fun ReviewCard(appInfo: SimpleAppInfo, review: Review) {
    val coroutineScope = rememberCoroutineScope()
    val reviewer = remember { mutableStateOf("") }
    coroutineScope.launch {
        reviewer.value = appInfo.client.getProfile(review.reviewerID).let { "${it.name} ${it.surname}" }
    }
    Card(elevation = 10.dp) {
        Row(modifier = Modifier.padding(20.dp)) {
            Text(review.body, fontSize = 16f.sp, modifier = Modifier.fillMaxWidth(0.3f).wrapContentHeight())
            Column {
                Text(
                    review.date, style = TextStyle(
                        fontSize = 12f.sp,
                        fontStyle = FontStyle.Italic
                    )
                )
                Text(
                    reviewer.value, style = TextStyle(
                        fontSize = 12f.sp,
                        fontStyle = FontStyle.Italic
                    )
                )
            }
        }
    }
}