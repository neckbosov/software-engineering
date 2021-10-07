package ui.utils

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun BoxWithVerticalScroll(modifier: Modifier, content: @Composable BoxScope.() -> Unit ) {
    val stateVertical = rememberScrollState(0)
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 10.dp, end = 10.dp)
    ) {
        Box(
            modifier = modifier
                .fillMaxSize(1f)
                .verticalScroll(stateVertical)
        ) {
            content()
        }
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd)
                .fillMaxHeight(),
            adapter = rememberScrollbarAdapter(stateVertical)
        )
    }
}
