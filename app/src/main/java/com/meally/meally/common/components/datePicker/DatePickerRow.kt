package com.meally.meally.common.components.datePicker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.meally.meally.common.components.BasicText
import com.meally.meally.common.components.HorizontalSpacer
import com.meally.meally.common.theme.Typography
import java.time.LocalDate

@Composable
fun DatePickerRow(
    label: String,
    selectedDate: LocalDate,
    onOpenDateSelection: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth()
    ) {
        BasicText(
            text = label,
            style = Typography.body1.copy(
                color = MaterialTheme.colorScheme.onBackground,
            ),
        )

        HorizontalSpacer(24.dp)

        DatePickerInput(
            selectedDate = selectedDate,
            onClick = onOpenDateSelection,
        )
    }
}