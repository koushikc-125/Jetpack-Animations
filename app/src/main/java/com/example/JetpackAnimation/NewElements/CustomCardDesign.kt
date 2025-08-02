package com.example.JetpackAnimation.NewElements

import android.annotation.SuppressLint
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.example.JetpackAnimation.designsystem.component.PrimaryScaffoldTopBar
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomCardDesign(
    onBack: () -> Unit, title: String
) {
    PrimaryScaffoldTopBar(
        showNavigationButton = true,
        navigationButtonAction = { onBack() }
    ) { padding ->
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(.32f)
                    .fillMaxWidth(.76f)
                    .background(
                        MaterialTheme.colorScheme.primary,
                        RoundedCornerShape(46.dp)
                    )
            ) {
                Container()
            }
        }
    }
}

@Composable
private fun Container() {
    Column(
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        TextContent()
        ImagesContent()
    }
}

@SuppressLint("FrequentlyChangingValue")
@Composable
private fun TextContent() {
    val backgroundColor = MaterialTheme.colorScheme.primary
    Box(
        modifier = Modifier
            .fillMaxHeight(.46f)
            .fillMaxWidth()
            .padding(top = 35.dp, start = 35.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(.15f)
                .fillMaxHeight()
                .padding(top = 10.dp)
                .align(alignment = Alignment.CenterEnd)
                .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                .zIndex(1f)
                .drawWithContent {
                    drawContent()
                    drawRect(
                        brush = Brush.horizontalGradient(
                            listOf(Color.Transparent, backgroundColor)
                        ), blendMode = BlendMode.DstOver
                    )
                }
        )
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            TitleTextComponent()
            DateList()
        }
    }
}

@Composable
private fun TitleTextComponent() {
    Column {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            LetterReveal(
                text = "Upcoming ",
                startDelay = 0.1,
                animationDuration = 0.2,
                style = MaterialTheme.typography.headlineSmall
            )
            LetterReveal(
                text = "events",
                startDelay = 0.3,
                animationDuration = 0.2,
                style = MaterialTheme.typography.headlineSmall
            )
        }
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            LetterReveal(
                text = "in  ",
                startDelay = 0.4,
                animationDuration = 0.5,
                style = MaterialTheme.typography.headlineSmall

            )
            LetterReveal(
                text = "NYC  ",
                startDelay = 0.5,
                animationDuration = 0.5,
                style = MaterialTheme.typography.headlineSmall
            )
            LetterReveal(
                text = "in ",
                startDelay = 0.6,
                animationDuration = 0.5,
                style = MaterialTheme.typography.headlineSmall
            )
            LetterReveal(
                text = "July",
                startDelay = 0.7,
                animationDuration = 0.5,
                style = MaterialTheme.typography.headlineSmall

            )
        }
    }
}

@Composable
private fun DateList() {
    val dateList = getDateList(startDay = 11, itemCount = 11)
    val pagerState = rememberPagerState(initialPage = 0) { dateList.size }
    var isVisible by remember { mutableStateOf(false) }
    val flingBehavior = PagerDefaults.flingBehavior(
        pagerState,
        pagerSnapDistance = PagerSnapDistance.atMost(1)
    )
    LaunchedEffect(Unit) {
        delay(900)
        isVisible = true
    }

    HorizontalPager(
        pagerState,
        snapPosition = SnapPosition.Start,
        beyondViewportPageCount = 11,
        pageSize = PageSize.Fixed(100.dp),
        flingBehavior = flingBehavior
    ) { it ->
        if (isVisible) {
            LetterReveal(
                text = dateList[it],
                startDelay = it * 0.15,
                animationDuration = 0.3,
               /* style = TextStyle(
                    color = if (it == pagerState.currentPage) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.onPrimary.copy(0.5f)
                    },
                    fontSize = 24.sp,
                    fontFamily = newFont,
                    fontWeight = FontWeight.SemiBold
                )*/
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Composable
private fun ImagesContent() {
    val urlList = listOf(
        "https://images.unsplash.com/photo-1681956959633-06035057d53d?q=80&w=687&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        "https://images.unsplash.com/photo-1676406422482-8c5bf043708b?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1yZWxhdGVkfDIzfHx8ZW58MHx8fHx8",
        "https://images.unsplash.com/photo-1741120171035-1facb5af693c?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1yZWxhdGVkfDM1fHx8ZW58MHx8fHx8"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clip(RoundedCornerShape(bottomStart = 46.dp, bottomEnd = 46.dp))
    ) {
        CardReveal(
            model = urlList[0],
            startDelay = 2000,
            targetRotation = -22f,
            targetOffsetX = (-55).dp,
            targetOffsetY = 80.dp,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
        CardReveal(
            model = urlList[1],
            startDelay = 1650,
            targetRotation = -8f,
            targetOffsetX = (-12).dp,
            targetOffsetY = 45.dp,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
        CardReveal(
            model = urlList[2],
            startDelay = 1300,
            targetRotation = -6f,
            targetOffsetX = -6.dp,
            targetOffsetY = 12.dp,
            modifier = Modifier.align(Alignment.BottomEnd)
        )

    }
}

private fun getDateList(startDay: Int, itemCount: Int): List<String> {
    val dateList = mutableListOf<String>()
    val calendar = Calendar.getInstance()

    // Set the starting day to "11" of current month and year
    calendar.set(Calendar.DAY_OF_MONTH, startDay)

    val formatter = SimpleDateFormat("EEE, dd", Locale.ENGLISH)

    repeat(itemCount) {
        dateList.add(formatter.format(calendar.time))
        calendar.add(Calendar.DAY_OF_MONTH, 1)
    }

    return dateList
}

@Composable
private fun LetterReveal(
    modifier: Modifier = Modifier,
    text: String,
    animationDuration: Double = 0.5,
    startDelay: Double = 0.0,
    style: TextStyle = LocalTextStyle.current
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay((startDelay * 1000).toLong())
        isVisible = true
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start
    ) {

        val animatedAlpha by animateFloatAsState(
            if (isVisible) 1f else 0f,
            tween(
                durationMillis = (animationDuration * 1000).toInt(),
                easing = EaseOut
            ),
            label = "alpha"
        )
        val animatedOffset by animateFloatAsState(
            if (isVisible) 0f else 20f,
            tween(
                (animationDuration * 1000).toInt(),
                easing = EaseOut
            ),
            label = "offset"
        )

        Text(
            text = text,
            style = style,
            modifier = modifier
                .graphicsLayer {
                    alpha = animatedAlpha
                    translationY = animatedOffset
                }
        )
    }
}

@Composable
private fun CardReveal(
    model: String,
    startDelay: Long = 1000L,
    targetRotation: Float = -5f,
    targetOffsetX: Dp = (-5).dp,
    targetOffsetY: Dp = 6.dp,
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(startDelay)
        isVisible = true
    }
    val animationOffsetY by animateDpAsState(
        targetValue = if (isVisible) targetOffsetY else 180.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessVeryLow
        ),
        label = "AnimationOffsetY"
    )
    val animationRotation by animateFloatAsState(
        targetValue = if (isVisible) targetRotation else 0f,
        animationSpec = tween(1200, easing = EaseOut),
        label = "AnimationRotation"
    )
    Card(
        modifier = modifier
            .width(160.dp)
            .fillMaxHeight()
            .offset(x = targetOffsetX, y = animationOffsetY)
            .rotate(animationRotation),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        AsyncImage(
            model = model,
            contentDescription = "Event Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}
