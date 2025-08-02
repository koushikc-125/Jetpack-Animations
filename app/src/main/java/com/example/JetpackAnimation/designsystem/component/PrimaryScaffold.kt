package com.example.JetpackAnimation.designsystem.component

import androidx.compose.animation.core.EaseIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.JetpackAnimation.designsystem.icon.ApplicationIcons
import com.example.JetpackAnimation.designsystem.theme.JetpackAnimationTheme
import com.example.JetpackAnimation.home.ui.ScaffoldSampleMode
import com.example.JetpackAnimation.home.ui.easedVerticalGradient
import dev.chrisbanes.haze.ExperimentalHazeApi
import dev.chrisbanes.haze.HazeInputScale
import dev.chrisbanes.haze.HazeProgressive
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalHazeMaterialsApi::class,
    ExperimentalHazeApi::class
)
@Composable
fun PrimaryScaffoldTopBar(
    appTitle: String? = null,
    modifier: Modifier = Modifier,
    showNavigationButton: Boolean = false,
    navigationButtonIcon: ImageVector = ApplicationIcons.Back,
    navigationButtonAction: () -> Unit = {},
    showActionButton: Boolean = false,
    actionButtonIcon: ImageVector = ApplicationIcons.Options,
    actionButtonAction: () -> Unit = {},
    showBottomBar: Boolean = false,
    bottomBarContent: @Composable () -> Unit = {},
    mode: ScaffoldSampleMode = ScaffoldSampleMode.Progressive,
    inputScale: HazeInputScale = HazeInputScale.Default,
    content: @Composable (PaddingValues) -> Unit
) {
    val hazeState = rememberHazeState()
    val style = HazeMaterials.regular(MaterialTheme.colorScheme.surface)

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                modifier = modifier
                    .padding(horizontal = 10.dp)
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
                title = {
                    if (appTitle != null) {
                        Text(
                            appTitle,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                },
                navigationIcon = {
                    if (showNavigationButton) {
                        SimpleButton(
                            onClick = { navigationButtonAction() },
                            icons = navigationButtonIcon
                        )
                    }
                },
                actions = {
                    if (showActionButton) {
                        SimpleButton(onClick = { actionButtonAction }, icons = actionButtonIcon)
                    }
                }
            )
        },
        bottomBar = {
            if (showBottomBar) {
                bottomBarContent()
            }
        },
        content = content
    )
}

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalHazeApi::class,
    ExperimentalHazeMaterialsApi::class, ExperimentalMaterial3ExpressiveApi::class
)
@Composable
fun PrimaryScaffoldCenterAlignedTopBar(
    appTitle: String? = null,
    modifier: Modifier = Modifier,
    showNavigationButton: Boolean = false,
    navigationButtonIcon: ImageVector = ApplicationIcons.Back,
    navigationButtonAction: () -> Unit = {},
    showActionButton: Boolean = false,
    actionButtonIcon: ImageVector = ApplicationIcons.Options,
    actionButtonAction: () -> Unit = {},
    showBottomBar: Boolean = false,
    bottomBarContent: @Composable () -> Unit = {},
    showFloatingActionButton: Boolean = false,
    floatingActionButtonIcon: ImageVector = ApplicationIcons.Badge,
    floatingActionButtonAction: () -> Unit = {},
    mode: ScaffoldSampleMode = ScaffoldSampleMode.Progressive,
    inputScale: HazeInputScale = HazeInputScale.Default,
    content: @Composable (PaddingValues) -> Unit
) {
    val hazeState = rememberHazeState()
    val style = HazeMaterials.regular(MaterialTheme.colorScheme.surface)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                modifier = modifier
                    .padding(horizontal = 10.dp)
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
                title = {
                    if (appTitle != null) {
                        Text(
                            appTitle,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                },
                navigationIcon = {
                    if (showNavigationButton) {
                        SimpleButton(
                            onClick = { navigationButtonAction() },
                            icons = navigationButtonIcon
                        )
                    }
                },
                actions = {
                    if (showActionButton) {
                        SimpleButton(onClick = { actionButtonAction }, icons = actionButtonIcon)
                    }
                }
            )
        },
        bottomBar = {
            if (showBottomBar) {
                bottomBarContent
            }
        },
        floatingActionButton = {
            if (showFloatingActionButton) {
                    FloatingActionButton(
                        onClick = floatingActionButtonAction
                        ) {
                        Icon(
                            floatingActionButtonIcon,
                            "Badge",
                        )
                    }
            }
        },
        content = content
    )
}

@OptIn(ExperimentalHazeApi::class)
@ThemePreview
@Composable
private fun SimpleScaffoldPreview() {
    val icons = ApplicationIcons.Back
    JetpackAnimationTheme {
        PrimaryScaffoldTopBar(
            appTitle = "Testing",
            mode = ScaffoldSampleMode.Progressive,
            inputScale = HazeInputScale.Default,
            showNavigationButton = true,
            navigationButtonIcon = icons,
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(10) { index ->
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Sample Item ${index + 1}",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "Description for item ${index + 1}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}