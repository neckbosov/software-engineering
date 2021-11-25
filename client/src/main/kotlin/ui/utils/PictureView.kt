package ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun PictureView(picture: ImageBitmap?, modifier: Modifier = Modifier) {
    if (picture != null) {
        Image(
            bitmap = picture,
            contentDescription = "avatar",
            contentScale = ContentScale.FillBounds,
            modifier = modifier.size(250.dp),

        )
    } else {
        Image(
            painter = painterResource("empty-avatar.jpg"),
            contentDescription = "avatar",
            modifier = modifier,
        )
    }
}