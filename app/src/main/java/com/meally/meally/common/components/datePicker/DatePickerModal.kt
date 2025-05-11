package com.meally.meally.common.components.datePicker

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.meally.meally.common.theme.MeallyTheme
import com.meally.meally.common.time.util.toEpochMillis
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    selectedDate: LocalDate = LocalDate.now(),
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate.toEpochMillis(),
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(
                    Instant.ofEpochMilli(datePickerState.selectedDateMillis ?: 0L).atZone(ZoneId.systemDefault()).toLocalDate()
                )
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        colors = getDateColors(),
    ) {
        DatePicker(
            state = datePickerState,
            colors = getDateColors(),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun getDateColors() = DatePickerDefaults.colors().copy(
    containerColor = MaterialTheme.colorScheme.background,
    titleContentColor = MaterialTheme.colorScheme.onBackground,
    headlineContentColor = MaterialTheme.colorScheme.onBackground,
    weekdayContentColor = MaterialTheme.colorScheme.onBackground,
    subheadContentColor = MaterialTheme.colorScheme.onBackground,
    navigationContentColor = MaterialTheme.colorScheme.onBackground,
    yearContentColor = MaterialTheme.colorScheme.onBackground,
    selectedYearContentColor = MaterialTheme.colorScheme.onBackground,
    selectedYearContainerColor = MaterialTheme.colorScheme.primary,
    dayContentColor = MaterialTheme.colorScheme.onBackground,
    selectedDayContentColor = MaterialTheme.colorScheme.onBackground,
    selectedDayContainerColor = MaterialTheme.colorScheme.primary,
    todayContentColor = MaterialTheme.colorScheme.onBackground,
    todayDateBorderColor = MaterialTheme.colorScheme.primary,
)

@Preview
@Composable
private fun DatePickerModalPreview() {
    MeallyTheme(darkTheme = true) {
        DatePickerModal(
            onDateSelected = {},
            onDismiss = {},
        )
    }
}