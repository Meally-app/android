package com.meally.meally.common.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.meally.meally.ui.theme.MeallyTheme
import com.meally.meally.ui.theme.Typography

@Composable
fun BasicButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        colors =
            ButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onBackground,
                disabledContainerColor = MaterialTheme.colorScheme.primary, // todo matko make disabled colors if needed
                disabledContentColor = MaterialTheme.colorScheme.onBackground,
            ),
        modifier = modifier,
        enabled = enabled,
    ) {
        BasicText(
            text = text,
            style =
                Typography.button.copy(
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                ),
            maxLines = 1,
        )
    }
}

@Preview
@Composable
private fun BasicButtonPreview() {
    MeallyTheme {
        BasicButton(
            text = "Example text",
            onClick = {},
        )
    }
}
