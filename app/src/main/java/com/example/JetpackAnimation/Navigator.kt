package com.example.JetpackAnimation

import androidx.compose.runtime.mutableStateListOf

class Navigator {

    private val backStack = mutableStateListOf<HomeCard>()

    val currentSample: HomeCard?
        get() = backStack.lastOrNull()

    fun navigateTo(sample: HomeCard) {
        backStack.add(sample)
    }

    fun navigateUp() {
        if (backStack.isNotEmpty()) {
            backStack.removeAt(backStack.lastIndex)
        }
    }
}