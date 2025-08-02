package com.example.JetpackAnimation.NewElements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.JetpackAnimation.home.ui.ScaffoldSampleMode
import dev.chrisbanes.haze.HazeInputScale
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.HazeMaterials

@Composable
fun TopBlur(
    onBack: () -> Unit,
    title: String, mode: ScaffoldSampleMode = ScaffoldSampleMode.Default,
    inputScale: HazeInputScale = HazeInputScale.Default,
) {
    val hazeState = remember { HazeState() }
    val style = HazeMaterials.regular(MaterialTheme.colorScheme.surface)

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .hazeEffect(state = hazeState, style = style) {
                        this.inputScale = inputScale
                    }
                    .height(100.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 41.dp)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Title",
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Icon(
                        Icons.Filled.Person,
                        contentDescription = "Person",
                        modifier = Modifier.size(38.dp)
                    )
                }
            }
        }
    ) { padding ->
        LazyVerticalStaggeredGrid(
            modifier = Modifier
                .hazeSource(state = hazeState)
                .fillMaxSize()
                .padding(horizontal = 5.dp),
            columns = StaggeredGridCells.Fixed(2),
            verticalItemSpacing = 1.dp,
            horizontalArrangement = Arrangement.spacedBy(1.dp),
            contentPadding = padding
        ) {
            items(50) {
                ElevatedCard(
                    colors = CardDefaults.elevatedCardColors(MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(8.dp)
                ) { }
            }
        }
    }
}
