package com.example.JetpackAnimation.NewElements

import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.JetpackAnimation.R
import com.example.JetpackAnimation.designsystem.component.PrimaryScaffoldTopBar
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextAnimation(onBack: () -> Unit, title: String) {

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

@Composable
private fun Container() {
    val isDark = isSystemInDarkTheme()
    val color = if (isDark) Color.Black else Color.White
    val painter = painterResource(R.drawable.ai_img)

    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(.88f)
            .shadow(8.dp, RoundedCornerShape(50.dp))
            .clip(RoundedCornerShape(50.dp))
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .drawWithContent {
                    drawContent()

                    val gradientHeight = size.height * 0.55f
                    val gradientTop = size.height - gradientHeight

                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                color
                            ),
                            startY = gradientTop,
                            endY = size.height
                        ),
                        topLeft = Offset(0f, gradientTop),
                        size = Size(size.width, gradientHeight)
                    )
                }
                .clip(RoundedCornerShape(50.dp))
        ) {
            Image(
                painter,
                "Home",
                Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(50.dp)),
                contentScale = ContentScale.Crop
            )
        }
        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(horizontal = 18.dp)
                .zIndex(2f)
                .clip(RoundedCornerShape(50.dp)),
            contentAlignment = Alignment.BottomStart
        ) {
            TextContent()
        }
    }
}

@Composable
private fun TextContent() {
    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = Modifier
            .paddingFromBaseline(bottom = 28.dp)
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LetterReveal(
                text = "Men Cap",
                style = MaterialTheme.typography.titleMedium,
                animationDuration = 0.6
            )
            LetterReveal(
                text = "Minimal. Timeless. Elevate",
                style = MaterialTheme.typography.titleMedium,
                animationDuration = 0.8
            )
        }
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            ShopButton()
        }

    }
}

@Composable
private fun ShopButton(
    durationMillis: Double = 0.9
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(200)
        isVisible = true
    }

    val animateAlpha by animateFloatAsState(
        if (isVisible) 1f else 0f,
        tween(
            (durationMillis * 1000).toInt(),
            easing = EaseOut
        ),
        label = "Button Alpha"
    )
    val animateOffset by animateFloatAsState(
        if (isVisible) 0f else 20f,
        tween(
            (durationMillis * 1000).toInt(),
            easing = EaseOut
        ),
        label = "Button YAxic"
    )

    Surface(
        modifier = Modifier
            .fillMaxHeight(0.081f)
            .fillMaxWidth(0.75f)
            .graphicsLayer {
                alpha = animateAlpha
                translationY = animateOffset
            },
        shape = RoundedCornerShape(50.dp),
        color = MaterialTheme.colorScheme.onBackground
    ) {
        Box(
            Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Shop Now",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.background.copy(0.8f),
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
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
    val finalStyle = style.merge(
        TextStyle(color = LocalContentColor.current.copy(alpha = 0.6f))
    )

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
            style = finalStyle,
            modifier = modifier
                .graphicsLayer {
                    alpha = animatedAlpha
                    translationY = animatedOffset
                }
        )
    }
}
