package com.meally.meally.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.meally.meally.common.theme.Typography

@Composable
fun InputRow(
    label: String,
    initialValue: String,
    onInputChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    var value by remember { mutableStateOf(initialValue) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier =
        modifier
            .fillMaxWidth()
            .height(56.dp),
    ) {
        BasicText(
            text = label,
            style =
            Typography.body1.copy(
                color = MaterialTheme.colorScheme.onBackground,
            ),
        )

        HorizontalSpacer(24.dp)

        BasicTextField(
            text = value,
            onTextChanged = {
                if (it.length <= 4) {
                    value = it
                    onInputChanged(value)
                }
            },
            textStyle =
            Typography.body1.copy(
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
            ),
            keyboardOptions = keyboardOptions,
            modifier = Modifier.width(92.dp),
        )
    }
}