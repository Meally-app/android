package com.meally.meally.common.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.meally.meally.R
import com.meally.meally.common.theme.MeallyTheme

@Composable
fun AppBar(
    @DrawableRes leadingIconResource: Int,
    onLeadingIconClicked: () -> Unit,
    @DrawableRes trailingIconResource: Int? = null,
    onTrailingIconClicked: () -> Unit = {},
    middleSection: @Composable () -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier =
            Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        AppBarIconButton(
            iconRes = leadingIconResource,
            onClick = onLeadingIconClicked,
        )
        middleSection()
        trailingIconResource?.let {
            AppBarIconButton(
                iconRes = it,
                onClick = onTrailingIconClicked,
            )
        }
    }
}

@Composable
fun AppBarIconButton(
    @DrawableRes iconRes: Int,
    onClick: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier =
        Modifier
            .size(40.dp)
            .background(MaterialTheme.colorScheme.surface, CircleShape)
            .clip(CircleShape)
            .clickable { onClick() }
            .padding(8.dp),
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Preview
@Composable
private fun AppBarPreview() {
    MeallyTheme {
        AppBar(
            leadingIconResource = R.drawable.ic_back,
            onLeadingIconClicked = {},
            trailingIconResource = R.drawable.ic_profile,
        )
    }
}
