package com.example.newapplication20.NewElements

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.BadgePlus
import com.composables.icons.lucide.ChevronLeft
import com.composables.icons.lucide.EllipsisVertical
import com.composables.icons.lucide.Lucide
import com.example.JetpackAnimation.Navigator
import com.example.JetpackAnimation.ui.theme.newFont

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MaskingBottomBar(navController: Navigator, title: String) {
    Scaffold(topBar = {
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
    }, bottomBar = {
        Box(
            Modifier
                .fillMaxWidth()
                .height(150.dp), Alignment.Center
        ) {
            FloatingActionButton({}) {
                Icon(Lucide.BadgePlus, "Plus")
            }
        }
    }) { padding ->
        val color1 = MaterialTheme.colorScheme.background
        Box(Modifier.fillMaxSize()) {
            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                    .drawWithContent {
                        drawContent()
                        drawRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    color1.copy(0.5f),// Top is fully visible
                                    color1             // Bottom fades to background color
                                ),
                                startY = size.height - 50.dp.toPx(), // Control where the fade starts
                                endY = size.height                   // Fade to bottom
                            ), blendMode = BlendMode.DstOut
                        )
                    }
                    .padding(top = padding.calculateTopPadding())
                    .padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.spacedBy(22.dp)
            ) {
                items(50) { items ->
                    ElevatedCard(
                        Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .padding(top = 5.dp),
                        colors = CardDefaults.elevatedCardColors(MaterialTheme.colorScheme.primary)
                    ) { }
                }
            }
            // Create a gradient overlay with multiple layers for a blur-like effect
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .align(Alignment.BottomCenter)
                    .graphicsLayer(alpha = 0.99f) // Force composition
                    .background(
                        Brush.verticalGradient(
                            0f to Color.Transparent,
                            0.3f to color1.copy(alpha = 0.1f),
                            0.5f to color1.copy(alpha = 0.3f),
                            0.7f to color1.copy(alpha = 0.5f),
                            1f to color1
                        )
                    )
            )
            // Add a second layer with slightly offset gradient for blur-like effect
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .align(Alignment.BottomCenter)
                    .graphicsLayer(alpha = 0.4f) // Semi-transparent
                    .background(
                        Brush.verticalGradient(
                            0f to Color.Transparent,
                            0.4f to color1.copy(alpha = 0.05f),
                            0.6f to color1.copy(alpha = 0.2f),
                            1f to color1.copy(alpha = 0.4f)
                        )
                    )
                    .blur(
                        radiusX = 50.dp,
                        radiusY = 100.dp,
                        edgeTreatment = BlurredEdgeTreatment.Unbounded
                    )
            )
        }
    }
}
