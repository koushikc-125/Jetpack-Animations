package com.example.JetpackAnimation.NewElements

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.JetpackAnimation.designsystem.component.PrimaryScaffoldTopBar
import com.example.JetpackAnimation.designsystem.theme.JetpackAnimationTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YouCanDoItPage(onBack: () -> Unit, title: String) {
    PrimaryScaffoldTopBar(
        showNavigationButton = true,
        navigationButtonAction = { onBack() }
    ) { padding ->
        Box(
            Modifier
                .padding(top = padding.calculateTopPadding())
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Container()
        }
    }
}

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
private fun Container() {
    val backgroundColor = MaterialTheme.colorScheme.background
    var isPressed by remember { mutableStateOf(false) }
    val itemHeight = 38.dp
    val containerHeight = 246.dp

    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(.5f)
                .height(containerHeight),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "You can",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(.85f)
                .height(if (isPressed) containerHeight else itemHeight),
            contentAlignment = Alignment.Center
        ) {
            if (isPressed) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.15f)
                        .align(alignment = Alignment.TopCenter)
                        .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                        .zIndex(1f)
                        .drawWithContent {
                            drawContent()
                            drawRect(
                                brush = Brush.verticalGradient(
                                    listOf(backgroundColor, Color.Transparent)
                                ),
                                blendMode = BlendMode.SrcOver
                            )
                        }
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.15f)
                        .align(alignment = Alignment.BottomCenter)
                        .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                        .zIndex(1f)
                        .drawWithContent {
                            drawContent()
                            drawRect(
                                brush = Brush.verticalGradient(
                                    listOf(Color.Transparent, backgroundColor)
                                ),
                                blendMode = BlendMode.SrcOver
                            )
                        }
                )
            }
            SelectText(isPressed) { isPressed = it }
        }
    }
}

@Composable
private fun SelectText(
    isPressed: Boolean, onPressedChange: (Boolean) -> Unit
) {
    val nameList = listOf(
        "design.",
        "solve.",
        "build.",
        "debug.",
        "learn.",
        "cook.",
        "ship.",
        "create.",
        "inspire.",
        "follow.",
        "test.",
        "teach.",
        "scale.",
        "do it."
    )
    var currentIndex by remember { mutableIntStateOf(0) }
    val pageCount = nameList.size
    val pagerState = rememberPagerState(initialPage = currentIndex) { pageCount }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .collect { page -> currentIndex = page }
    }

    VerticalPager(
        state = pagerState,
        userScrollEnabled = false,
        pageSpacing = if (isPressed) (-194).dp else 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .pointerInput(pagerState) {
                detectDragGesturesAfterLongPress(
                    // 1) finger has been held long enough -> expand
                    onDragStart = {
                        onPressedChange(true)
                    },
                    // 2) every drag event, scroll the pager
                    onDrag = { change, dragAmount ->
                        change.consume()
                        // scrollBy takes pixels; negative because you want the finger-down direction
                        coroutineScope.launch {
                            pagerState.scrollBy(-dragAmount.y)
                        }
                    },
                    // 3) finger up -> snap & collapse
                    onDragEnd = {
                        coroutineScope.launch {
                            // animate to the nearest page
                            pagerState.animateScrollToPage(pagerState.currentPage)
                            onPressedChange(false)
                        }
                    },
                    // if the drag gets cancelled (e.g. interruption), collapse
                    onDragCancel = {
                        onPressedChange(false)
                    }
                )
            }
    ) {
        Text(
            nameList[it],
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Magenta,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = if (isPressed) 104.dp else 0.dp)
        )
    }
}

@Preview
@Composable
private fun ContainerPreview() {
    Container()
}

@Preview()
@Composable
private fun YouCanDoScreenPreviewLightTheme() {
    JetpackAnimationTheme(darkTheme = false) {
        YouCanDoItPage({}, "Preview Title")
    }
}

@Preview()
@Composable
private fun YouCanDoScreenPreviewDarkTheme() {
    JetpackAnimationTheme(darkTheme = true) {
        YouCanDoItPage({}, "Preview Title")
    }
}

@PreviewFontScale
@PreviewScreenSizes
@Preview(name = "main")
@Composable
private fun YouCanDoScreenPreview() {
    JetpackAnimationTheme {
        YouCanDoItPage({}, "Preview Title")
    }
}
