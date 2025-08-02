package com.example.JetpackAnimation.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.JetpackAnimation.NewElements.CustomCardDesign
import com.example.JetpackAnimation.NewElements.DeleteAlertPage
import com.example.JetpackAnimation.NewElements.DotGesturePage
import com.example.JetpackAnimation.NewElements.DoubleSliderPage
import com.example.JetpackAnimation.NewElements.HowOldAreYou
import com.example.JetpackAnimation.NewElements.OutlineOrbitPage
import com.example.JetpackAnimation.NewElements.PaymentCardApp
import com.example.JetpackAnimation.NewElements.Perplexity
import com.example.JetpackAnimation.NewElements.TextAnimation
import com.example.JetpackAnimation.NewElements.TopBlur
import com.example.JetpackAnimation.NewElements.YouCanDoItPage
import com.example.newapplication20.NewElements.MaskingbbPage
import com.example.newapplication20.NewElements.PagerAnimationPage

@Composable
fun DetailScreen(
    id: Int,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (id) {
        1 -> PagerAnimationPage({ onBack() }, "PagerAnimation")
        2 -> MaskingbbPage(onBack, "BottomBar")
        3 -> DoubleSliderPage(onBack, "Slider")
        4 -> DotGesturePage(onBack, "Dots")
        5 -> PaymentCardApp(onBack, "Payment Card")
        6 -> TopBlur(onBack, "Top Bar")
        7 -> Perplexity(onBack, "News")
        8 -> DeleteAlertPage(onBack, "Alert")
        9 -> TextAnimation(onBack, "Text Animation")
        10 -> HowOldAreYou(onBack, "How old are you?")
        11 -> CustomCardDesign(onBack , "Custom Card")
        12 -> YouCanDoItPage(onBack,"You Can")
        13 -> OutlineOrbitPage(onBack)
        else -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Content not found for ID: $id")
            }
        }
    }
}