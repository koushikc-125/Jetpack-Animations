package com.example.benchmark

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.benchmark.macro.junit4.BaselineProfileRule
import org.junit.Rule
import org.junit.Test

class BaselineProfileGenerator {

    @RequiresApi(Build.VERSION_CODES.P)
    @get:Rule
    val baselineRule = BaselineProfileRule()

    @RequiresApi(Build.VERSION_CODES.P)
    @Test
    fun generateBaselineProfile() = baselineRule.collect(
        packageName = "com.example.JetpackAnimationTheme"
    ) {
        pressHome()
        startActivityAndWait()
        addElementsAndScrollDown()
    }
}