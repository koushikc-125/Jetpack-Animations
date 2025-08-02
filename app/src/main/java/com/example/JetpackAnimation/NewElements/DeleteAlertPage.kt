package com.example.JetpackAnimation.NewElements

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType.Companion.LongPress
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.JetpackAnimation.R
import com.example.JetpackAnimation.designsystem.component.PrimaryScaffoldTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteAlertPage(
    onBack: () -> Unit,
    title: String
) {
    PrimaryScaffoldTopBar(
        showNavigationButton = true,
        navigationButtonAction = { onBack() }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .safeContentPadding(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxHeight(0.45f)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(35.dp),
                color = MaterialTheme.colorScheme.errorContainer
            ) {
                DeleteContent()
            }
        }
    }
}

@Composable
private fun DeleteContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(MaterialTheme.colorScheme.error, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painterResource(R.drawable.baseline_error_outline_24),
                    "Error",
                    tint = MaterialTheme.colorScheme.errorContainer
                )
            }
            Text("Are you sure?", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Medium,
                            color = LocalContentColor.current.copy(.6f)
                        )
                    ) {
                        append("Deleting your account is permanent.\n")
                        append("You'll lose access to all your posts, messages, followers, and saved content.")
                    }
                },
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Medium,
                            color = LocalContentColor.current.copy(.6f)
                        )
                    ) {
                        append("This action can't be undone.")
                    }
                }
            )
            DeleteButton()
        }
    }
}

@Composable
private fun DeleteButton() {
    var isPressed by remember { mutableStateOf(false) }
    val hapticFeedback = LocalHapticFeedback.current

    val animatedCount by animateFloatAsState(
        if (isPressed) 1f else 0f,
        animationSpec = if (isPressed) {
            tween(2000, easing = LinearEasing)
        } else {
            tween(200, easing = FastOutSlowInEasing)
        },
        label = "Width animation"
    )
    val animatedScaling by animateFloatAsState(
        if (isPressed) 0.95f else 1f,
        tween(150, easing = LinearEasing),
        label = "Button title padding"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 6.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        hapticFeedback.performHapticFeedback(LongPress)
                        isPressed = true
                        try {
                            awaitRelease()
                        } finally {
                            isPressed = false
                        }
                    }
                )
            }
            .clip(RoundedCornerShape(24.dp))
            .background(
                MaterialTheme.colorScheme.background, RoundedCornerShape(24.dp)
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            "Hold to Delete",
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.sp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .graphicsLayer {
                    scaleX = animatedScaling
                    scaleY = animatedScaling
                },
            textAlign = TextAlign.Center
        )
        Box(
            Modifier
                .fillMaxWidth(animatedCount)
                .height(48.dp)
                .background(MaterialTheme.colorScheme.onErrorContainer)
        )
        Text(
            "Hold to Delete",
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.sp,
            color = MaterialTheme.colorScheme.errorContainer,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .drawWithContent {
                    drawContent()
                }
                .graphicsLayer {
                    scaleX = animatedScaling
                    scaleY = animatedScaling
                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .drawWithContent {
                    val overlayWidth = size.width * animatedCount
                    clipRect(right = overlayWidth) {
                        this@drawWithContent.drawContent()
                    }
                },
            textAlign = TextAlign.Center
        )
    }
}
