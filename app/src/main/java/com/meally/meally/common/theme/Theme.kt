package com.meally.meally.common.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import com.meally.meally.common.components.SetStatusBarColor

private val DarkColorScheme =
    darkColorScheme(
        primary = Color(0xFFDA2C38),
        secondary = Color(0xFF2C2C54),
        background = Color(0xFF222222),
        surface = Color(0xFF50514F),
        onBackground = Color.White,
    )

private val LightColorScheme =
    lightColorScheme(
        primary = Color(0xFFDA2C38),
        secondary = Color(0xFF2C2C54),
        background = Color(0xFFFBFBFF),
        surface = Color(0xFFEDE7D9),
        onBackground = Color.Black,
    )

@Composable
fun MeallyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme =
        when {
            darkTheme -> DarkColorScheme
            else -> LightColorScheme
        }

    if (!LocalInspectionMode.current) {
        SetStatusBarColor(colorScheme.background, useDarkIcons = !darkTheme)
    }

    MaterialTheme(colorScheme = colorScheme, typography = Typography.materialTypography, content = content)
}
