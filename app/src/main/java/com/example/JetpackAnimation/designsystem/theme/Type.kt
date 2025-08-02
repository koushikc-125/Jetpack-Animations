package com.example.JetpackAnimation.designsystem.theme

import com.example.JetpackAnimation.R
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun typography(
    font: Int = R.font.inter
): Typography {
    val selectedFont = FontFamily(Font(font))

    return Typography(
        displayLarge = TextStyle(
            fontFamily = selectedFont,
            fontWeight = FontWeight.Black,
            fontSize = 57.sp ,
            lineHeight = 64.sp,
            letterSpacing = -(0.25).sp,
        ),
        displayMedium = TextStyle(
            fontFamily = selectedFont,
            fontWeight = FontWeight.Black,
            fontSize = 45.sp,
            lineHeight = 52.sp
        ),
        displaySmall = TextStyle(
            fontFamily = selectedFont,
            fontWeight = FontWeight.Black,
            fontSize = 36.sp,
            lineHeight = 44.sp
        ),
        headlineLarge = TextStyle(
            fontFamily = selectedFont,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp ,
            lineHeight = 40.sp
        ),
        headlineMedium = TextStyle(
            fontFamily = selectedFont,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp ,
            lineHeight = 36.sp
        ),
        headlineSmall = TextStyle(
            fontFamily = selectedFont,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp ,
            lineHeight = 32.sp
        ),
        titleLarge = TextStyle(
            fontFamily = selectedFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp ,
        ),
        titleMedium = TextStyle(
            fontFamily = selectedFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp ,
            lineHeight = 24.sp ,
        ),
        titleSmall = TextStyle(
            fontFamily = selectedFont,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp ,
            lineHeight = 20.sp ,
            letterSpacing = 0.1.sp
        ),
        labelLarge = TextStyle(
            fontFamily = selectedFont,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp ,
            lineHeight = 16.sp ,
            letterSpacing = 0.1.sp
        ),
        labelMedium = TextStyle(
            fontFamily = selectedFont,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp ,
            lineHeight = 14.sp,
            letterSpacing = 0.5.sp
        ),
        labelSmall = TextStyle(
            fontFamily = selectedFont,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp ,
            lineHeight = 12.sp ,
            letterSpacing = 0.5.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = selectedFont,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp ,
            lineHeight = 24.sp ,
            letterSpacing = 0.5.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = selectedFont,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp ,
            lineHeight = 20.sp ,
            letterSpacing = 0.25.sp
        ),
        bodySmall = TextStyle(
            fontFamily = selectedFont,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp ,
            lineHeight = 16.sp ,
            letterSpacing = 0.4.sp
        )
    )
}
