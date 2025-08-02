package com.example.JetpackAnimation.NewElements

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.JetpackAnimation.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Perplexity(
    onBack: () -> Unit,
    title: String, modifier: Modifier = Modifier
) {

    Scaffold()
    { padding ->
        VerticalContent()
    }
}

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
private fun VerticalContent() {
    val spacing = 4.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val pagerHeight = (screenHeight * 0.75f)
    val pagerState = rememberPagerState { 10 }
    VerticalPager(
        pagerState,
        modifier = Modifier.fillMaxSize(),
        pageSize = PageSize.Fixed(pagerHeight),
        pageSpacing = spacing * 2,
        contentPadding = PaddingValues(
            top = (screenHeight - pagerHeight) / 2,
            bottom = (screenHeight - pagerHeight) / 2
        )
    ) { page ->
        VerticalItems(pagerHeight, spacing, page)
    }
}

@Composable
private fun VerticalItems(pagerHeight: Dp, spacing: Dp, index: Int) {
    val painter = painterResource(R.drawable.home)
    Box(
        modifier = Modifier
            .padding(spacing * 5)
            .height(pagerHeight-50.dp)
    ) {
        Image(
            painter = painter,
            "Home",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp))
                .blur(20.dp),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White.copy(alpha = 0.3f)) // Adjust alpha for transparency
        )
        Column(
            modifier = Modifier
                .padding(15.dp)
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painter,
                "Home",
                modifier = Modifier.clip(
                    RoundedCornerShape(25.dp)
                )
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Page: $index",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
