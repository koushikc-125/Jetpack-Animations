package com.example.JetpackAnimation.NewElements

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.JetpackAnimation.designsystem.component.PrimaryScaffoldTopBar
import kotlinx.coroutines.launch
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HowOldAreYou(onBack: () -> Unit, title: String) {
    val range = 18..65
    val itemCount = range.count()
    val lazyListState = rememberLazyListState()
    val middleItemIndex by remember {
        derivedStateOf {
            val layoutInfo = lazyListState.layoutInfo
            val visibleItemsInfo = layoutInfo.visibleItemsInfo

            if (visibleItemsInfo.isEmpty()) {
                null
            } else {
                val viewportCenter =
                    layoutInfo.viewportStartOffset + layoutInfo.viewportSize.height / 2

                visibleItemsInfo.minByOrNull { itemInfo ->
                    val itemCenter = itemInfo.offset + itemInfo.size / 2
                    abs(itemCenter - viewportCenter)
                }?.index ?: -1
            }
        }
    }

    PrimaryScaffoldTopBar(
        showNavigationButton = true,
        navigationButtonAction = { onBack() }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    title, style = MaterialTheme.typography.headlineSmall
                )
                NumberContent(range, itemCount, lazyListState, middleItemIndex)
                ButtonComponent(itemCount, lazyListState, middleItemIndex)
            }
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope", "RememberReturnType")
@Composable
private fun NumberContent(
    range: IntRange,
    itemCount: Int,
    lazyListState: LazyListState,
    middleItemIndex: Int?
) {
    val snapBehavior = rememberSnapFlingBehavior(
        lazyListState = lazyListState, snapPosition = SnapPosition.Center
    )
    val color = MaterialTheme.colorScheme.primary.copy(0.2f)
    val backgroundColor = MaterialTheme.colorScheme.background

    Box(
        modifier = Modifier
            .fillMaxWidth(.72f)
            .fillMaxHeight(.68f)
            .drawBehind {
                val height = size.height
                val width = size.width
                val halfHeight = height / 2
                val halfWidth = width / 2
                val strokeWidth = 1.dp.toPx()
                val lineSpace = 26.dp.toPx()
                val length = width * .25f

                drawLine(
                    color = color,
                    start = Offset(x = halfWidth - length, y = halfHeight - lineSpace),
                    end = Offset(x = halfWidth + length, y = halfHeight - lineSpace),
                    strokeWidth = strokeWidth
                )

                drawLine(
                    color = color,
                    start = Offset(x = halfWidth - length, y = halfHeight + lineSpace),
                    end = Offset(x = halfWidth + length, y = halfHeight + lineSpace),
                    strokeWidth = strokeWidth
                )
            }
    ) {
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
        BoxWithConstraints {
            val boxHeight = maxHeight
            val boxCenter = boxHeight / 2

            LazyColumn(
                state = lazyListState,
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                flingBehavior = snapBehavior,
            ) {
                items(itemCount) { it ->
                    NumberList(it, itemCount, boxCenter, lazyListState, it == middleItemIndex)
                }
            }
        }
    }
}

@Composable
private fun NumberList(
    index: Int,
    itemCount: Int,
    boxCenter: Dp,
    lazyListState: LazyListState,
    isSelected: Boolean = false
) {
    val lastItem = index == itemCount - 1
    val firstItem = index == 0
    remember {
        derivedStateOf {
            val layoutInfo = lazyListState.layoutInfo
            val visibleItem = layoutInfo.visibleItemsInfo.find { it.index == index }

            if (visibleItem != null) {
                val itemCenter = visibleItem.offset + (visibleItem.size / 2)
                val screenCenter = layoutInfo.viewportSize.height / 2
                val distanceFromCenter = abs(itemCenter - screenCenter).toFloat()
                val maxDistance = screenCenter.toFloat()

                // Calculate scale: 1.0 at center, 0.6 at maximum distance
                val normalizedDistance = (distanceFromCenter / maxDistance).coerceIn(0f, 1f)
                (1f - (normalizedDistance * 0.4f)).coerceIn(0.6f, 1f)
            } else {
                0.8f // Default scale for non-visible items
            }
        }
    }

    Text(
        "${index + 1}",
        modifier = Modifier
            .padding(
                top = if (firstItem) boxCenter - 23.dp else 2.dp,
                bottom = if (lastItem) boxCenter - 23.dp else 2.dp
            ),
        style = MaterialTheme.typography.displaySmall,
        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground.copy(
            0.5f
        )
    )
}

@Composable
private fun ButtonComponent(itemCount: Int, lazyListState: LazyListState, middleItemIndex: Int?) {
    val coroutineScope = rememberCoroutineScope()
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(.88f)
                .height(64.dp)
                .background(
                    MaterialTheme.colorScheme.primary, RoundedCornerShape(14.dp)
                )
                .clickable {
                    coroutineScope.launch {
                        val layoutInfo = lazyListState.layoutInfo
                        val centeredIndex = middleItemIndex ?: lazyListState.firstVisibleItemIndex
                        val targetIndex = (centeredIndex + 1).coerceAtMost(itemCount - 1)
                        val itemHeight = layoutInfo.visibleItemsInfo.firstOrNull()?.size ?: 0
                        val viewportHeight =
                            layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset

                        lazyListState.animateScrollToItem(
                            index = targetIndex,
                            scrollOffset = -(viewportHeight / 2) + (itemHeight / 2)
                        )
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Next Year",
                style = MaterialTheme.typography.titleMedium
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(.88f)
                .height(64.dp)
                .clickable {
                    coroutineScope.launch {
                        val layoutInfo = lazyListState.layoutInfo
                        val centeredIndex = middleItemIndex ?: lazyListState.firstVisibleItemIndex
                        val targetIndex = (centeredIndex - 1).coerceAtMost(itemCount - 1)
                        val itemHeight = layoutInfo.visibleItemsInfo.firstOrNull()?.size ?: 0
                        val viewportHeight =
                            layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset

                        lazyListState.animateScrollToItem(
                            index = targetIndex,
                            scrollOffset = -(viewportHeight / 2) + (itemHeight / 2)
                        )
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Previous Year",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
