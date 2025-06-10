package com.example.JetpackAnimation.NewElements

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedContentLambdaTargetStateParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoubleSlider(
    navController: Navigator, title: String
) {
    var show by remember { mutableStateOf(false) }
    var thumbShift by remember { mutableStateOf(false) }
    var secondThumbShift by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val selectedColor = MaterialTheme.colorScheme.onPrimary
    val unselectedColor = MaterialTheme.colorScheme.onSurface
    val horizontalPadding = 8.dp

    val thumbOffset by animateDpAsState(
        targetValue = if (!thumbShift) horizontalPadding else with(LocalDensity.current) {
            // Calculate the maximum available width
            val totalWidth = (LocalConfiguration.current.screenWidthDp * 0.9f).dp
            val thumbWidth = totalWidth / 2
            // Calculate end position with the same padding
            totalWidth - thumbWidth - horizontalPadding
        }, animationSpec = spring(
            Spring.DampingRatioMediumBouncy, Spring.StiffnessLow
        ), label = "thumbAnimation"
    )
    val offsetX by animateDpAsState(
        targetValue = if (!secondThumbShift) 44.dp else 132.dp,
        animationSpec = tween(durationMillis = 300) // Adjust duration as needed
    )
    val textup by animateDpAsState(
        targetValue = if (!thumbShift) 0.dp else (-45).dp, animationSpec = spring(
            Spring.DampingRatioMediumBouncy, Spring.StiffnessLow
        )
    )
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(modifier = Modifier.padding(horizontal = 10.dp), title = {
                Text(title, fontFamily = newFont, fontWeight = FontWeight.Bold)
            }, navigationIcon = {
                Box(
                    Modifier
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
                .padding(padding), contentAlignment = Alignment.Center
        ) {

            OverFlowText(textup,thumbShift) { thumbShift = !thumbShift }

            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(60.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(30.dp)),
                shape = RoundedCornerShape(30.dp),
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 8.dp, bottom = 8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .offset(x = thumbOffset)
                                .fillMaxWidth(0.5f)
                                .fillMaxHeight()
                                .background(
                                    MaterialTheme.colorScheme.primaryContainer,
                                    RoundedCornerShape(25.dp)
                                )
                        )
                    }
                    Box(
                        modifier = Modifier
                            .offset(x = offsetX)
                            .padding(top = 12.dp, bottom = 12.dp)
                    ) {
                        AnimatedVisibility(
                            visible = thumbShift,
                            enter = fadeIn(initialAlpha = 0.1f) + scaleIn(
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow)
                            ) ,
                            exit = fadeOut(targetAlpha = 0.1f) + scaleOut()
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.25f)
                                    .fillMaxHeight()
                                    .background(
                                        MaterialTheme.colorScheme.primary, RoundedCornerShape(25.dp)
                                    )
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(0.5f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Free",
                                fontFamily = newFont,
                                color = if (thumbShift) LocalContentColor.current.copy(0.5f) else LocalContentColor.current.copy(
                                    1f
                                ),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.clickable { thumbShift = !thumbShift })
                        }
                        if (thumbShift) {
                            RowText(secondThumbShift) { secondThumbShift = !secondThumbShift }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OverFlowText(textup: Dp, thumbShiftCheck: Boolean, thumbShift: () -> Unit) {
    Text(
        "Premium",
        fontFamily = newFont,
        color = if (!thumbShiftCheck) LocalContentColor.current.copy(0.5f) else LocalContentColor.current.copy(
            1f
        ),
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .offset(x = 90.dp, y = textup)
            .zIndex(1f)
            .clickable { thumbShift() })
}

@Composable
fun RowText(secondThumbShift: Boolean, onToggle: () -> Unit) {
    AnimatedVisibility(
        visible = true, enter = scaleIn(
            initialScale = 0.1f, // Start at 10% scale for more dramatic effect
            animationSpec = tween(durationMillis = 900) // Longer duration (900ms)
        ), exit = scaleOut(
            targetScale = 0.1f, // End at 10% scale when disappearing
            animationSpec = tween(durationMillis = 500) // Exit animation duration
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .offset(x = (-5).dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceAround
        ) {
            Text(
                "Solo",
                color = if (secondThumbShift) Color.White else Color.Black,
                fontFamily = newFont,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onToggle() }  // Call the function to update the state
            )
            Text(
                "Team",
                color = if (!secondThumbShift) Color.White else Color.Black,
                fontFamily = newFont,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onToggle() }  // Call the function to update the state
            )
        }
    }
}

@Preview
@Composable
private fun Show() {
    val navController = Navigator()
    DoubleSlider(navController, "Name")
}