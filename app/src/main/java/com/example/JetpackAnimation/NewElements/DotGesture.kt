package com.example.JetpackAnimation.NewElements

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.composables.icons.lucide.ChevronLeft
import com.composables.icons.lucide.EllipsisVertical
import com.composables.icons.lucide.Lucide
import com.example.JetpackAnimation.Navigator
import com.example.JetpackAnimation.ui.theme.newFont
import kotlin.math.sqrt

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DotGesture(navController: Navigator, title: String) {
    var fingerPosition by remember { mutableStateOf<Offset?>(null) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(title, fontFamily = newFont, fontWeight = FontWeight.Bold)
                }, navigationIcon = {
                    Box(
                        Modifier
                            .padding(horizontal = 10.dp)
                            .size(40.dp)
                            .clip(CircleShape)
                            .border(0.4.dp, MaterialTheme.colorScheme.primary, CircleShape)
                            .background(MaterialTheme.colorScheme.onPrimary)
                            .clickable { navController.navigateUp() }, Alignment.Center
                    ) {
                        Icon(
                            Lucide.ChevronLeft, "Back", Modifier.size(20.dp)
                        )
                    }
                }, actions = {
                    Box(
                        Modifier
                            .padding(horizontal = 10.dp)
                            .size(40.dp)
                            .clip(CircleShape)
                            .border(0.4.dp, MaterialTheme.colorScheme.primary, CircleShape)
                            .background(MaterialTheme.colorScheme.onPrimary)
                            .clickable {},
                        Alignment.Center
                    ) {
                        Icon(
                            Lucide.EllipsisVertical, "User", Modifier.size(20.dp)
                        )
                    }
                })
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding())
                .gridBackground(fingerPosition = fingerPosition)
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            event.changes.forEach { change ->
                                fingerPosition = if (change.pressed) {
                                    change.position
                                } else {
                                    null // Clear when finger is lifted
                                }

                            }
                        }
                    }
                }
        )
    }
}

@Composable
fun Modifier.gridBackground(
    fingerPosition: Offset? = null,
    dotColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
    dotSpacing: Dp = 20.dp,
    dotRadius: Dp = 2.dp,
    effectRadius: Dp = 80.dp, // Radius of effect around finger
    maxBrightness: Float = 1f // Maximum brightness multiplier
): Modifier = this.drawWithCache {
    val spacingPx = dotSpacing.toPx()
    val radiusPx = dotRadius.toPx()
    val effectRadiusPx = effectRadius.toPx()


    // Pre-calculate grid dimensions and offsets
    val columns = (size.width / spacingPx).toInt()
    val rows = (size.height / spacingPx).toInt()
    val horizontalOffset = (size.width - (columns - 1) * spacingPx) / 2
    val verticalOffset = (size.height - (rows - 1) * spacingPx) / 2

    // Pre-calculate dot positions for better performance
    val dotPositions = mutableListOf<Offset>()
    for (row in 0..rows) {
        for (col in 0..columns) {
            val x = horizontalOffset + col * spacingPx
            val y = verticalOffset + row * spacingPx

            if (x in radiusPx..(size.width - radiusPx) &&
                y in radiusPx..(size.height - radiusPx)
            ) {
                dotPositions.add(Offset(x, y))
            }
        }
    }

    onDrawBehind {
        drawOptimizedGrid(
            dotPositions = dotPositions,
            fingerPosition = fingerPosition,
            dotColor = dotColor,
            radiusPx = radiusPx,
            effectRadiusPx = effectRadiusPx,
            maxBrightness = maxBrightness
        )
    }
}

private fun DrawScope.drawOptimizedGrid(
    dotPositions: List<Offset>,
    fingerPosition: Offset?,
    dotColor: Color,
    radiusPx: Float,
    effectRadiusPx: Float,
    maxBrightness: Float
) {
    if (fingerPosition == null) {
        // Fast path: draw all dots with same color when no finger interaction
        dotPositions.forEach { dotCenter ->
            drawCircle(
                color = dotColor,
                radius = radiusPx,
                center = dotCenter
            )
        }
    } else {
        // Optimized path: only calculate brightness for dots within effect radius
        val effectRadiusSquared = effectRadiusPx * effectRadiusPx

        dotPositions.forEach { dotCenter ->
            val dx = dotCenter.x - fingerPosition.x
            val dy = dotCenter.y - fingerPosition.y
            val distanceSquared = dx * dx + dy * dy

            val currentDotColor = if (distanceSquared <= effectRadiusSquared) {
                val distance = sqrt(distanceSquared)
                val brightnessFactor = (1f - (distance / effectRadiusPx)) * maxBrightness
                val baseAlpha = dotColor.alpha
                val newAlpha = (baseAlpha + brightnessFactor * (1f - baseAlpha)).coerceAtMost(1f)
                dotColor.copy(alpha = newAlpha)
            } else {
                dotColor
            }

            drawCircle(
                color = currentDotColor,
                radius = radiusPx,
                center = dotCenter
            )
        }
    }
}
