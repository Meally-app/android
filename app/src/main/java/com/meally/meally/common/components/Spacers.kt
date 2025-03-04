package com.meally.meally.common.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun HorizontalSpacer(width: Dp) {
    Spacer(Modifier.width(width))
}

@Composable
fun VerticalSpacer(height: Dp) {
    Spacer(Modifier.height(height))
}
