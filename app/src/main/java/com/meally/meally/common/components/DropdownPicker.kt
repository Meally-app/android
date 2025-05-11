package com.meally.meally.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.meally.meally.R
import com.meally.meally.common.theme.Typography

@Composable
fun <T> DropdownPicker(
    optionMap: Map<String, T>,
    onOptionClicked: (T) -> Unit,
    modifier: Modifier = Modifier,
) {

    var isDropdownExpanded by remember { mutableStateOf(false) }

    var selectedOption by remember { mutableStateOf(optionMap.keys.minOrNull() ?: "") }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .border(
                width = if (isDropdownExpanded) 2.dp else 1.dp,
                color = if (isDropdownExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(100.dp)
            )
            .background(MaterialTheme.colorScheme.background, RoundedCornerShape(100.dp))
            .clickable { isDropdownExpanded = !isDropdownExpanded }
            .padding(vertical = 12.dp)
            .padding(end = 12.dp, start = 4.dp)
    ) {
        DropdownMenu(
            expanded = isDropdownExpanded,
            onDismissRequest = {
                isDropdownExpanded = false
            },
            modifier = Modifier.background(MaterialTheme.colorScheme.surface)
        ) {
            optionMap.entries.sortedBy { it.key }.forEach {
                DropdownMenuItem(
                    text = {
                        BasicText(
                            text = it.key,
                            style = Typography.body2,
                        )
                    },
                    onClick = {
                        onOptionClicked(it.value)
                        selectedOption = it.key
                        isDropdownExpanded = false
                    },
                    colors = MenuItemColors(
                        textColor = MaterialTheme.colorScheme.onBackground,
                        leadingIconColor = MaterialTheme.colorScheme.onBackground,
                        trailingIconColor = MaterialTheme.colorScheme.onBackground,
                        disabledTextColor = MaterialTheme.colorScheme.onBackground,
                        disabledLeadingIconColor = MaterialTheme.colorScheme.onBackground,
                        disabledTrailingIconColor = MaterialTheme.colorScheme.onBackground,
                    ),
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_down_menu),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(24.dp)
            )

            BasicText(
                text = selectedOption,
                style = Typography.body1.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                ),
            )
        }

    }
}

@Preview
@Composable
private fun DropdownPickerPreview() {
    DropdownPicker(
        mapOf(
            "Breakfast" to "breakfast",
            "Lunch" to "lunch",
            "Dinner" to "dinner",
            "Snack" to "snack",
        ),
        onOptionClicked = {},
    )
}