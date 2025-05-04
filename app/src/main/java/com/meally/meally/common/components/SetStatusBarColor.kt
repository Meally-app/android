package com.meally.meally.common.components

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat

@Composable
fun SetStatusBarColor(
    color: Color,
    useDarkIcons: Boolean,
) {
    val context = LocalContext.current
    val activity = context as Activity
    SideEffect {
        val window = activity.window
        window.statusBarColor = color.toArgb()
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = useDarkIcons
    }
}
