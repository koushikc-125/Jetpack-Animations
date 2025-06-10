package com.example.JetpackAnimation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Menu
import com.composables.icons.lucide.User
import com.example.JetpackAnimation.NewElements.DotGesture
import com.example.JetpackAnimation.NewElements.DoubleSlider
import com.example.JetpackAnimation.NewElements.PaymentCardApp
import com.example.JetpackAnimation.NewElements.TopBlur
import com.example.JetpackAnimation.ui.theme.newFont
import com.example.newapplication20.NewElements.MaskingBottomBar
import com.example.newapplication20.NewElements.PagerAnimation
import kotlinx.coroutines.launch

data class HomeCard(
    val number: Int,
    val title: String,
    val subtitle: String,
    val img: Int,
    val content: @Composable (Navigator) -> Unit,
)

val data = listOf(
    HomeCard(
        1, "Horizontal Pager", "23.4.2025", R.drawable.horizontal_pager_change_via_button,
        { PagerAnimation(it, "PagerAnimation") }
    ),
    HomeCard(
        2, "Masking", "28.4.2025", R.drawable.masking,
        { MaskingBottomBar(it, "DarkBottomBar") }
    ),
    HomeCard(
        3, "Slider", "11.5.2025", R.drawable.doubleslider,
        { DoubleSlider(it, "Slider") }
    ),
    HomeCard(
        4, "Dots", "coming soon", R.drawable.dots_gestures,
        { DotGesture(it, "Dots") }
    ),
    HomeCard(
        5, "Payment Card", "coming soon", R.drawable.dots_gestures,
        { PaymentCardApp(it, "Payment Card") }
    ),
    HomeCard(
        6, "Top Bar", "coming soon", R.drawable.dots_gestures,
        { TopBlur(it, "Top Bar") }
    ),
)

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "Range")
@Composable
fun Home(
    appTitle: String,
    navigator: Navigator = remember { Navigator() },
    samples: List<HomeCard> = data
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val currentCard = navigator.currentSample

    if (currentCard != null) {
        currentCard.content(navigator)
        return
    }

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Drawer Title",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleLarge
                    )
                    HorizontalDivider()

                    Text(
                        "Section 1",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                    NavigationDrawerItem(
                        label = { Text("Item 1") },
                        selected = false,
                        onClick = { /* Handle click */ }
                    )
                    NavigationDrawerItem(
                        label = { Text("Item 2") },
                        selected = false,
                        onClick = { /* Handle click */ }
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(
                        "Section 2",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                    NavigationDrawerItem(
                        label = { Text("Settings") },
                        selected = false,
                        icon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
                        badge = { Text("20") }, // Placeholder
                        onClick = { /* Handle click */ }
                    )
                    NavigationDrawerItem(
                        label = { Text("Help and feedback") },
                        selected = false,
                        icon = {
                            Icon(
                                Icons.AutoMirrored.Outlined.ExitToApp,
                                contentDescription = null
                            )
                        },
                        onClick = { /* Handle click */ },
                    )
                    Spacer(Modifier.height(12.dp))
                }
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    modifier = Modifier
                        .padding(horizontal = 10.dp),
                    title = {
                        Text(appTitle, fontFamily = newFont, fontWeight = FontWeight.Bold)
                    },
                    navigationIcon = {
                        Box(
                            Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .border(0.4.dp, MaterialTheme.colorScheme.primary, CircleShape)
                                .background(MaterialTheme.colorScheme.onPrimary)
                                .clickable {
                                    scope.launch {
                                        if (drawerState.isClosed) {
                                            drawerState.open()
                                        } else {
                                            drawerState.close()
                                        }
                                    }
                                },
                            Alignment.Center
                        ) {
                            Icon(
                                Lucide.Menu, "Back", Modifier.size(20.dp)
                            )
                        }
                    },
                    actions = {
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
                                Lucide.User, "User", Modifier.size(20.dp)
                            )
                        }
                    }
                )
            })
        { padding ->
            LazyColumn(
                Modifier
                    .padding(padding)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 20.dp)
                    .padding(top = 5.dp),
                verticalArrangement = Arrangement.spacedBy(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(samples) { data ->
                    ElevatedCard(
                        Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .clickable { navigator.navigateTo(data) },
                        elevation = CardDefaults.elevatedCardElevation(12.dp),
                        colors = CardDefaults.elevatedCardColors(MaterialTheme.colorScheme.primary),
                        shape = CardDefaults.elevatedShape,

                        ) {
                        Row(
                            Modifier
                                .fillMaxSize()
                                .padding(horizontal = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                Modifier
                                    .fillMaxHeight()
                                    .weight(1f),
                                Arrangement.Center,
                                Alignment.Start
                            ) {
                                Text(
                                    data.title,
                                    fontFamily = newFont,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(Modifier.height(5.dp))
                                Text(
                                    data.subtitle,
                                    fontFamily = newFont,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = LocalContentColor.current.copy(0.6f)
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(0.30f)
                            ) {
                                Image(
                                    painter = painterResource(data.img),
                                    contentDescription = "Image for ${data.title}",
                                    modifier = Modifier
                                        .matchParentSize()
                                        .graphicsLayer {
                                            scaleX = 1.6f // Increase image size horizontally
                                            scaleY = 1.6f // Increase image size vertically
                                            translationY = 140f // Move image up to show top portion
                                        },
                                    contentScale = ContentScale.Fit // We'll control the scale manually
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun show() {
    val navController = remember { Navigator() }
    Home("Home", navController)
}