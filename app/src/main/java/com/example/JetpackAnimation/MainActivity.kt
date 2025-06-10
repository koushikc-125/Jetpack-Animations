package com.example.JetpackAnimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.JetpackAnimation.ui.theme.JetpackAnimationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val title = stringResource(id = R.string.title)
            val navigator = remember { Navigator() }
            val hasBackStack = navigator.currentSample != null
            BackHandler(enabled = hasBackStack) {
                navigator.navigateUp()
            }
            JetpackAnimationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Home(
                        appTitle = title,
                        navigator = navigator,
                    )
                }
            }
        }
    }
}
