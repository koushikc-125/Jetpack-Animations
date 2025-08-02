package com.example.JetpackAnimation.home.ui

import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.Easing
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Scale
import com.example.JetpackAnimation.R
import com.example.JetpackAnimation.designsystem.component.PrimaryScaffoldTopBar
import com.example.JetpackAnimation.designsystem.component.ThemePreview
import com.example.JetpackAnimation.designsystem.theme.JetpackAnimationTheme
import com.example.JetpackAnimation.home.data.HomeScreenCard
import dev.chrisbanes.haze.ExperimentalHazeApi
import dev.chrisbanes.haze.HazeInputScale
import dev.chrisbanes.haze.HazeProgressive
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState

val data = listOf(
    HomeScreenCard(1,"Horizontal Pager","23.4.2025", R.drawable.horizontal_pager_with_button),
    HomeScreenCard(2, "Masking", "28.4.2025", R.drawable.masking),
    HomeScreenCard(3, "Slider", "11.5.2025", R.drawable.doubleslider),
    HomeScreenCard(4, "Dots", "17.6.25", R.drawable.dots_gestures),
    HomeScreenCard(5, "Payment Card", "24.12.24", R.drawable.payment_success),
    HomeScreenCard(6, "Top Bar", "27.4.25", R.drawable.blur_topbar),
    HomeScreenCard(7, "Perplexity", "coming soon", R.drawable.perplexity_explore_page),
    HomeScreenCard(8, "Delete Alert", "27.6.25", R.drawable.are_you_sure),
    HomeScreenCard(9, "Text Animation", "28.6.25", R.drawable.text_animation),
    HomeScreenCard(10, "How old are you", "2.7.25", R.drawable.how_old_are_you),
    HomeScreenCard(11, "Custom Card", "8.7.25", R.drawable.custom_card),
    HomeScreenCard(12, "You Can", "31.7.25", R.drawable.you_can_do_it),
    HomeScreenCard(13, "Outline Orbit", "2.8.25", R.drawable.cooking),
)

enum class ScaffoldSampleMode {
    Default,
    Progressive,
    Mask,
}

@Composable
fun Home(
    appTitle: String,
    onClick: (Int) -> Unit,
    samples: List<HomeScreenCard> = data,
    mode: ScaffoldSampleMode = ScaffoldSampleMode.Progressive,
    inputScale: HazeInputScale = HazeInputScale.Default,
) {
    HomeContent(appTitle, samples, mode, inputScale, onClick)
}

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalHazeApi::class,
    ExperimentalHazeMaterialsApi::class
)
@Composable
private fun HomeContent(
    appTitle: String,
    samples: List<HomeScreenCard>,
    mode: ScaffoldSampleMode,
    inputScale: HazeInputScale,
    onClick: (Int) -> Unit,
) {
    val hazeState = rememberHazeState()
    val style = HazeMaterials.regular(MaterialTheme.colorScheme.surface)

    PrimaryScaffoldTopBar(
        appTitle = appTitle,
        modifier = Modifier
            .hazeEffect(state = hazeState, style = style) {
                this.inputScale = inputScale

                when (mode) {
                    ScaffoldSampleMode.Default -> Unit
                    ScaffoldSampleMode.Progressive -> {
                        progressive = HazeProgressive.verticalGradient(
                            startIntensity = 1f,
                            endIntensity = 0f,
                        )
                    }

                    ScaffoldSampleMode.Mask -> {
                        mask = Brush.easedVerticalGradient(EaseIn)
                    }
                }
            },
        mode = ScaffoldSampleMode.Progressive,
        inputScale = HazeInputScale.Default,
    ) { padding ->
        LazyColumn(
            Modifier
                .fillMaxSize()
                .hazeSource(state = hazeState)
                .padding(horizontal = 20.dp)
                .testTag("Item_list"),
            verticalArrangement = Arrangement.spacedBy(22.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding(),
                bottom = WindowInsets.safeDrawing.asPaddingValues().calculateBottomPadding()
            )
        ) {
            items(samples, key = { it.id }) { data ->
                HomeCardItem(data) { onClick(data.id) }
            }
        }
    }
}


@Composable
private fun HomeCardItem(
    data: HomeScreenCard,
    onClick: () -> Unit
) {
    ElevatedCard(
        Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { onClick() },
        elevation = CardDefaults.elevatedCardElevation(6.dp),
        shape = RoundedCornerShape(18.dp),
        ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                Modifier
                    .fillMaxHeight()
                    .weight(1f),
                Arrangement.Center,
                Alignment.Start
            ) {
                Text(
                    data.title,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.height(5.dp))
                Text(
                    data.subtitle,
                    style = MaterialTheme.typography.titleMedium,
                    color = LocalContentColor.current.copy(0.6f)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.30f)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(data.img)
                        .crossfade(true)
                        .size(800)
                        .scale(Scale.FIT)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .build(),

                    contentDescription = "Image for ${data.title}",
                    modifier = Modifier
                        .matchParentSize()
                        .graphicsLayer {
                            scaleX = 1.6f
                            scaleY = 1.6f
                            translationY = 140f
                            compositingStrategy = CompositingStrategy.Offscreen
                        },
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

fun Brush.Companion.easedGradient(
    easing: Easing,
    start: Offset = Offset.Zero,
    end: Offset = Offset.Infinite,
    numStops: Int = 16,
): Brush {
    val colors = List(numStops) { i ->
        val x = i * 1f / (numStops - 1)
        Color.Black.copy(alpha = 1f - easing.transform(x))
    }

    return linearGradient(colors = colors, start = start, end = end)
}

fun Brush.Companion.easedVerticalGradient(
    easing: Easing,
    startY: Float = 0.0f,
    endY: Float = Float.POSITIVE_INFINITY,
    numStops: Int = 16,
): Brush = easedGradient(
    easing = easing,
    numStops = numStops,
    start = Offset(x = 0f, y = startY),
    end = Offset(x = 0f, y = endY),
)

@ThemePreview
@Composable
private fun HomeScreenPreview() {
    val appTitle = "Jetpack Animation"
    JetpackAnimationTheme {
        HomeContent(
            appTitle,
            data,
            ScaffoldSampleMode.Progressive,
            HazeInputScale.Default,
            { }
        )
    }
}
