package com.meally.meally.screens.weightEntry.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.meally.meally.R
import com.meally.meally.common.components.AppBar
import com.meally.meally.common.components.BasicButton
import com.meally.meally.common.components.InputRow
import com.meally.meally.common.components.VerticalSpacer
import com.meally.meally.common.components.datePicker.DatePickerModal
import com.meally.meally.common.components.datePicker.DatePickerRow
import com.meally.meally.common.components.focusClearer
import com.meally.meally.common.theme.MeallyTheme
import com.meally.meally.screens.weightEntry.viewModel.WeightEntryViewModel
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

data class WeightEntryScreenNavArgs(
    val date: LocalDate
)

@Destination(navArgsDelegate = WeightEntryScreenNavArgs::class)
@Composable
fun WeightEntryScreen(
    viewModel: WeightEntryViewModel = koinViewModel()
) {
    val date = viewModel.viewState.collectAsStateWithLifecycle().value

    WeightEntryScreenStateless(
        selectedDate = date,
        onBackClicked = viewModel::goBack,
        onAmountChanged = viewModel::weightChanged,
        onDateSelected = viewModel::dateSelected,
        onConfirmClicked = viewModel::confirm
    )
}

@Composable
private fun WeightEntryScreenStateless(
    selectedDate: LocalDate,
    onBackClicked: () -> Unit = {},
    onAmountChanged: (String) -> Unit = {},
    onDateSelected: (LocalDate) -> Unit = {},
    onConfirmClicked: () -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .focusClearer(LocalFocusManager.current),
    ) {
        AppBar(
            leadingIconResource = R.drawable.ic_back,
            onLeadingIconClicked = onBackClicked,
        )
        Content(
            selectedDate = selectedDate,
            onAmountChanged = onAmountChanged,
            onDateSelected = onDateSelected,
            onConfirmClicked = onConfirmClicked,
        )
    }
}

@Composable
private fun Content(
    selectedDate: LocalDate,
    onAmountChanged: (String) -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    onConfirmClicked: () -> Unit,
) {
    var isDatePickerShown by remember { mutableStateOf(false) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            InputRow(
                label = "Weight (kg):",
                initialValue = "",
                onInputChanged = onAmountChanged,
                keyboardOptions =
                KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number,
                ),
            )

            VerticalSpacer(8.dp)

            DatePickerRow(
                label = "Date",
                selectedDate = selectedDate,
                onOpenDateSelection = { isDatePickerShown = true },
            )

            Spacer(Modifier.weight(1f))

            BasicButton(
                text = "Confirm",
                onClick = onConfirmClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
            )
        }
        if (isDatePickerShown) {
            DatePickerModal(
                selectedDate = selectedDate,
                onDateSelected = onDateSelected,
                onDismiss = { isDatePickerShown = false },
            )
        }
    }
}

@Preview
@Composable
private fun WeightEntryScreenPreview() {
    MeallyTheme {
        WeightEntryScreenStateless(
            selectedDate = LocalDate.now()
        )
    }

}