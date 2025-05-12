package com.meally.meally.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun OutlinedBasicButton(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(12.dp),
    onClick: (() -> Unit)?,
    content: @Composable () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(100.dp)
            )
            .background(MaterialTheme.colorScheme.background, RoundedCornerShape(100.dp))
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
            .padding(padding)
    ) {
        content()
    }
}