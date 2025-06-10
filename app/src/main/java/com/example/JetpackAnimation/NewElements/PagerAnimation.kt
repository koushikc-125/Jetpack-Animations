package com.example.newapplication20.NewElements

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.composables.icons.lucide.ChevronLeft
import com.composables.icons.lucide.EllipsisVertical
import com.composables.icons.lucide.Lucide
import com.example.JetpackAnimation.Navigator
import com.example.JetpackAnimation.ui.theme.newFont
import kotlinx.coroutines.launch

data class List(
    val number: Int,
)

val cardItem = listOf(
    List(1),
    List(2),
    List(3),
    List(4),
)

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedContentLambdaTargetStateParameter")
@Composable
fun PagerAnimation(
    navController: Navigator,
    title: String,
) {
    val pagerState = rememberPagerState { cardItem.size }
    var autoScroll by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .padding(horizontal = 10.dp),
                title = {
                    Text(title, fontFamily = newFont, fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    Box(
                        Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .border(0.4.dp, MaterialTheme.colorScheme.primary, CircleShape)
                            .background(MaterialTheme.colorScheme.onPrimary)
                            .clickable { navController.navigateUp() },
                        Alignment.Center
                    ) {
                        Icon(
                            Lucide.ChevronLeft,
                            "Back",
                            Modifier
                                .size(20.dp)
                                .offset(x = -1.dp)
                        )
                    }
                },
                actions = {
                    var showDropDown by remember { mutableStateOf(false) }

                    Box(
                        Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .border(0.4.dp, MaterialTheme.colorScheme.primary, CircleShape)
                            .background(MaterialTheme.colorScheme.onPrimary)
                            .clickable { showDropDown = !showDropDown },
                        Alignment.Center
                    ) {
                        Icon(
                            Lucide.EllipsisVertical, "Theme Options",
                            Modifier.size(24.dp)
                        )
                    }
                    if (showDropDown) {
                        DropdownMenu(
                            showDropDown,
                            { showDropDown = !showDropDown },
                        ) {
                            DropdownMenuItem(
                                {
                                    Text(
                                        "Auto Scroll",
                                        fontFamily = newFont,
                                        fontWeight = FontWeight.Bold
                                    )
                                },
                                { autoScroll = !autoScroll }
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            BottomButtons(pagerState)
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize(),
            Arrangement.Center,
            Alignment.CenterHorizontally
        ) {
            PagerElement(pagerState)
            Spacer(Modifier.height(15.dp))
            DotComponent(pagerState)
        }
    }
}

@Composable
fun DotComponent(pagerState: PagerState) {
    val dotSize = 20.dp
    val spaceBetween = 10.dp
    val backgroundWidthDp by remember(pagerState.currentPage) {
        derivedStateOf {
            dotSize * (pagerState.currentPage + 1) + spaceBetween * pagerState.currentPage
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .clipToBounds(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .zIndex(1f)
                .clipToBounds()
        ) {
            // Calculate the width directly in dp (no conversion needed)
            val totalDotWidth = remember {
                dotSize * cardItem.size + spaceBetween * (cardItem.size - 1)
            }
            val animatedWidth by animateDpAsState(
                targetValue = backgroundWidthDp,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                ),
                label = "background width animation"
            )

            // Create a box that contains both the background and dots
            Box(Modifier.width(totalDotWidth + 5.dp), contentAlignment = Alignment.CenterStart) {
                // Background highlight that grows from left to right
                Box(
                    modifier = Modifier

                        .height(dotSize)
                        .width(animatedWidth)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .zIndex(0f)
                )
                // Dots row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.zIndex(1f)
                ) {
                    cardItem.forEachIndexed { index, _ ->
                        val isSelected = pagerState.currentPage == index
                        InnerDot(dotSize, isSelected)
                        if (index != cardItem.lastIndex) {
                            Spacer(Modifier.width(spaceBetween))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InnerDot(dotSize: Dp, isSelected: Boolean) {
    val selectedColor = MaterialTheme.colorScheme.primary
    val unSelectedColor = MaterialTheme.colorScheme.onSurface.copy(0.4f)
    Canvas(
        modifier = Modifier
            .size(dotSize)
    ) {
        drawCircle(
            if (isSelected) selectedColor else unSelectedColor,
            size.minDimension / 4
        )
    }
}

@Composable
fun PagerElement(pagerState: PagerState) {

//    LaunchedEffect(Unit) {
//        while(true) {
//            delay(2000)
//            val target = if (pagerState.currentPage < pagerState.pageCount - 1) pagerState.currentPage + 1 else 0
//            pagerState.animateScrollToPage(
//                page = target,
//                animationSpec = tween(
//                    durationMillis = 500,
//                    easing = FastOutSlowInEasing
//                )
//            )
//        }
//    }

    HorizontalPager(
        pagerState,
        modifier = Modifier.testTag("horizontal_pager") // Add this line
    ) { pager ->
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            ElevatedCard(
                Modifier
                    .size(
                        350.dp, 200.dp
                    ),
                colors = CardDefaults.elevatedCardColors(MaterialTheme.colorScheme.primary)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No ${pager + 1}",
                        fontFamily = newFont,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun BottomButtons(pagerState: PagerState) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier.align(Alignment.Center),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AnimatedVisibility(visible = pagerState.currentPage > 0) {
                BackButton(pagerState)
            }
            AnimatedVisibility(visible = pagerState.currentPage > 0) {
                Spacer(Modifier.width(5.dp))
            }
            ContinueButton(pagerState)
        }
    }
}

@Composable
fun BackButton(pagerState: PagerState) {
    val coroutineScope = rememberCoroutineScope()
    val isLastPage = pagerState.currentPage == cardItem.size - 1
    var buttonState by remember { mutableStateOf(isLastPage) }

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            buttonState = pagerState.currentPage == cardItem.size - 1
        }
    }
    Button(
        onClick = {
            coroutineScope.launch {
                val next = (pagerState.currentPage - 1).coerceAtMost(pagerState.pageCount - 1)
                pagerState.animateScrollToPage(next)
            }
        },
        modifier = Modifier.testTag("Back Icon").width(80.dp),
        enabled = !pagerState.isScrollInProgress,
        contentPadding = PaddingValues()
    ) {
        Icon(Lucide.ChevronLeft, "Back Icon",Modifier.size(30.dp))
    }
}

@Composable
fun ContinueButton(pagerState: PagerState) {
    val coroutineScope = rememberCoroutineScope()
    val isLastPage = pagerState.currentPage == cardItem.size - 1
    var buttonState by remember { mutableStateOf(isLastPage) }

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            buttonState = pagerState.currentPage == cardItem.size - 1
        }
    }

    Button(
        onClick = {
            coroutineScope.launch {
                val nextPage = (pagerState.currentPage + 1).coerceAtMost(pagerState.pageCount - 1)
                pagerState.animateScrollToPage(nextPage)
            }
        },
        enabled = !pagerState.isScrollInProgress,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("Continue")
    ) {
        AnimatedContent(
            targetState = buttonState,
            transitionSpec = {
                if (targetState) {
                    // Transitioning to "Sign up?"
                    // "Sign up?" slides in from bottom to top
                    (slideInVertically(initialOffsetY = { height -> height }) + fadeIn())
                        .togetherWith(
                            // "Continue" slides out toward the top
                            slideOutVertically(targetOffsetY = { height -> -height }) + fadeOut()
                        )
                } else {
                    // Transitioning to "Continue"
                    // "Continue" appears in the middle and expands in place
                    (slideInVertically(initialOffsetY = { height -> -height }) + fadeIn())
                        .togetherWith(
                            // "Continue" slides out toward the top
                            slideOutVertically(targetOffsetY = { height -> height }) + fadeOut()
                        )
                }
            },
            label = "ButtonTextAnimation"
        ) { change ->
            Text(
                if (change) "Sign up?" else "Continue",
                fontFamily = newFont,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
private fun Show() {
    val nav = Navigator()
    PagerAnimation(nav, "Name")
}
