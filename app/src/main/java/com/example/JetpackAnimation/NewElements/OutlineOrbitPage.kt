package com.example.JetpackAnimation.NewElements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.example.JetpackAnimation.designsystem.component.PrimaryScaffoldTopBar
import com.example.JetpackAnimation.designsystem.component.ThemePreview
import com.example.JetpackAnimation.designsystem.theme.JetpackAnimationTheme
import com.example.JetpackAnimation.ui.OutlineOrbit

@Composable
fun OutlineOrbitPage(
    onBack: () -> Unit,
    appTitle: String? = null
) {
    PageContainer(onBack)
}

@Composable
fun PageContainer(onBack: () -> Unit) {

    PrimaryScaffoldTopBar(
        showNavigationButton = true,
        navigationButtonAction = onBack
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            NewButtonContent()
        }
    }
}

@Composable
fun NewButtonContent() {
    OutlineOrbit() {
        Text(
            "Button",
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@ThemePreview
@Composable
private fun OutlineOrbitPagePreview() {
    JetpackAnimationTheme {
        PageContainer({})
    }
}

@PreviewFontScale
@PreviewScreenSizes
@Composable
private fun OutlineOrbitPreviewWithFontScaleAndScreenSizes() {
    JetpackAnimationTheme {
        PageContainer({})
    }
}
