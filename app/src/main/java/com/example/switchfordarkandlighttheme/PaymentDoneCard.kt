package com.example.switchfordarkandlighttheme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex


@Composable
fun PaymentCardApp(modifier: Modifier = Modifier) {
    var show by remember { mutableStateOf(false) }
    val scale = remember {
        Animatable(1f)
    }
    val animatedTextColor by animateColorAsState(
        targetValue = if (show) Color.Black else Color.White,
        animationSpec = tween(durationMillis = 500)
    )

    val animatedAlpha by animateFloatAsState(
        targetValue = if (show) 1f else 0f,
        animationSpec = tween(durationMillis = 500)
    )

    val animatedScale by animateFloatAsState(
        targetValue = if (show) 1f else 0f,
        animationSpec = tween(durationMillis = 500)
    )

    LaunchedEffect(key1 = show) {
        scale.animateTo(
            targetValue = if (show) 15f else 1f,
            animationSpec = tween(
                durationMillis = 800,
            )
        )
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ElevatedCard(
            elevation = CardDefaults.elevatedCardElevation(15.dp),
            modifier = Modifier
                .size(150.dp)
                .scale(2f),
            colors = CardDefaults.elevatedCardColors(Color.Black),
            shape = RoundedCornerShape(12.dp),
        ) {
            Box(modifier = Modifier.fillMaxSize()) {

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 10.dp, end = 10.dp)
                        .size(25.dp)
                        .scale(scale.value)
                        .background(Color.White, shape = CircleShape)
                        .clickable {
                            show = !show
                        }
                        .zIndex(0f)
                ) {}

                Text(
                    "$25",
                    color = animatedTextColor,
                    fontSize = 50.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .zIndex(1f)
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 10.dp, end = 10.dp)
                        .size(25.dp)
                        .graphicsLayer(
                            scaleX = animatedScale,
                            scaleY = animatedScale,
                            alpha = animatedAlpha
                        )
                        .background(color = Color.Blue, shape = CircleShape)
                        .zIndex(2f),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.Check,
                        contentDescription = "Check",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun show1() {
    PaymentCardApp()
}