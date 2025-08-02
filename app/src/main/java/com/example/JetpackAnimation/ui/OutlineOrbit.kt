package com.example.JetpackAnimation.ui

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.JetpackAnimation.designsystem.component.ThemePreview
import com.example.JetpackAnimation.designsystem.theme.JetpackAnimationTheme

@Composable
fun OutlineOrbit(
    orbitNumber: Int? = 0,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val infiniteTransition = rememberInfiniteTransition(label = "dots_movement")

    val color = MaterialTheme.colorScheme.primary

    var isPressed by remember { mutableStateOf(false) }

    var (dashThird, gapThird) = calculateDashGap(320.dp, 152.dp)
    gapThird -= 5.dp
    dashThird -= 5.dp
    val phaseOffsetDpThird by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (dashThird + gapThird).value,
        animationSpec = infiniteRepeatable(
            animation = tween(300, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase_offset"
    )
    val phaseOffsetPxThird = with(density) { phaseOffsetDpThird.dp.toPx() }

    var (dashSecond, gapSecond) = calculateDashGap(260.dp, 104.dp)
    gapSecond -= 3.dp
    dashSecond -= 3.dp
    val phaseOffsetDpSecond by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -(dashSecond + gapSecond).value,
        animationSpec = infiniteRepeatable(
            animation = tween(300, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase_offset"
    )
    val phaseOffsetPxSecond = with(density) { phaseOffsetDpSecond.dp.toPx() }

    val dash = 8.dp
    val gap = 4.89.dp
    val phaseOffsetDp by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (dash + gap).value,
        animationSpec = infiniteRepeatable(
            animation = tween(300, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase_offset"
    )
    val phaseOffsetPx = with(density) { phaseOffsetDp.dp.toPx() }

    val bgColorAlpha by animateFloatAsState(
        if (isPressed) 0.15f else 0.09f,
        tween(300, easing = EaseInOut),
        label = "AnimatedColorAlpha"
    )
    val secondOutlineColorAlpha by animateFloatAsState(
        if (isPressed) 0.38f else 0.3f,
        tween(300, easing = EaseInOut),
        label = "AnimatedColorAlpha"
    )
    val thirdOutlineColorAlpha by animateFloatAsState(
        if (isPressed) 0.18f else 0.1f,
        tween(300, easing = EaseInOut),
        label = "AnimatedColorAlpha"
    )

    Box(
        modifier = Modifier
            .size(320.dp, 152.dp)
            .drawBehind {
                val dashPx = dashThird.toPx()
                val gapPx = gapThird.toPx()

                drawRoundRect(
                    color = color.copy(thirdOutlineColorAlpha),
                    style = Stroke(
                        width = 2.dp.toPx(),
                        pathEffect = PathEffect
                            .dashPathEffect(
                                floatArrayOf(dashPx, gapPx),
                                phaseOffsetPxThird
                            )
                    ),
                    cornerRadius = CornerRadius(100.dp.toPx())
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(260.dp, 104.dp)
                .drawBehind {
                    val dashPx = dashSecond.toPx()
                    val gapPx = gapSecond.toPx()

                    drawRoundRect(
                        color = color.copy(secondOutlineColorAlpha),
                        style = Stroke(
                            width = 2.dp.toPx(),
                            pathEffect = PathEffect
                                .dashPathEffect(
                                    floatArrayOf(dashPx, gapPx),
                                    phaseOffsetPxSecond
                                )
                        ),
                        cornerRadius = CornerRadius(50.dp.toPx())
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp, 56.dp)
                    .background(
                        color = color.copy(alpha = bgColorAlpha),
                        shape = RoundedCornerShape(25.dp)
                    )
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                isPressed = true
                                try {
                                    tryAwaitRelease()
                                } finally {
                                    isPressed = false
                                }
                            }
                        )
                    }
                    .drawBehind {
                        val dashPx = dash.toPx()
                        val gapPx = gap.toPx()

                        drawRoundRect(
                            color = color,
                            style = Stroke(
                                width = 2.dp.toPx(),
                                pathEffect = PathEffect
                                    .dashPathEffect(
                                        floatArrayOf(dashPx, gapPx),
                                        phaseOffsetPx
                                    )
                            ),
                            cornerRadius = CornerRadius(50.dp.toPx())
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                content
            }
        }
    }
}


data class DashGap(val dash: Dp, val gap: Dp)

fun calculateDashGap(
    width: Dp,
    height: Dp,
    desiredDashUnits: Float = 39.7f,
    gapRatio: Float = 0.38f
): DashGap {
    val perimeter = 2 * (width + height)
    val dashUnit = perimeter / desiredDashUnits
    val gap = dashUnit * gapRatio
    val dash = dashUnit - gap
    return DashGap(dash = dash, gap = gap)
}

@ThemePreview
@Composable
private fun OutlineOrbitPreview() {
    JetpackAnimationTheme {
        OutlineOrbit(3) {
            Text(
                "Button",
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}
