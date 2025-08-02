package com.example.JetpackAnimation.designsystem.component

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonShapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.JetpackAnimation.designsystem.icon.ApplicationIcons
import com.example.JetpackAnimation.designsystem.theme.JetpackAnimationTheme

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SimpleButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    icons: ImageVector
) {
    FilledTonalIconButton(
        modifier = modifier,
        onClick = onClick,
        shapes = IconButtonShapes(
            shape = CircleShape,
            pressedShape = RoundedCornerShape(10.dp)
        )
    ) {
        Icon(
            imageVector = icons,
            contentDescription = "Save"
        )
    }
}

@ThemePreview
@Composable
private fun SimpleButtonPreview() {
    JetpackAnimationTheme {
        SimpleButton(onClick = {}, icons = ApplicationIcons.Back)
    }
}
