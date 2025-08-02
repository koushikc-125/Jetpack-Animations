package com.example.JetpackAnimation.NewElements

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.example.JetpackAnimation.designsystem.component.PrimaryScaffoldTopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedContentLambdaTargetStateParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoubleSliderPage(
    onBack: () -> Unit,
    title: String
) {
    PrimaryScaffoldTopBar(
        showNavigationButton = true,
        navigationButtonAction = { onBack() }
    ) { padding ->
    }
}
