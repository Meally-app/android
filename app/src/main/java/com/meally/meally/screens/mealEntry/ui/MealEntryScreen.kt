package com.meally.meally.screens.mealEntry.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.meally.domain.mealType.MealType
import com.meally.meally.R
import com.meally.meally.common.components.AppBar
import com.meally.meally.common.components.BasicButton
import com.meally.meally.common.components.BasicText
import com.meally.meally.common.components.DropdownPicker
import com.meally.meally.common.components.HorizontalSpacer
import com.meally.meally.common.components.InputRow
import com.meally.meally.common.components.VerticalSpacer
import com.meally.meally.common.components.datePicker.DatePickerModal
import com.meally.meally.common.components.datePicker.DatePickerRow
import com.meally.meally.common.components.focusClearer
import com.meally.meally.common.food.viewState.FoodInfoViewState
import com.meally.meally.common.food.viewState.FoodItemViewState
import com.meally.meally.common.theme.MeallyTheme
import com.meally.meally.common.theme.Typography
import com.meally.meally.screens.foodEntry.ui.model.FoodEntryViewState
import com.meally.meally.screens.mealEntry.ui.model.MealEntryViewState
import com.meally.meally.screens.mealEntry.ui.model.MealViewState
import com.meally.meally.screens.mealEntry.viewModel.MealEntryViewModel
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

data class MealEntryScreenNavArgs(
    val mealId: String,
)

@Destination(navArgsDelegate = MealEntryScreenNavArgs::class)
@Composable
fun MealEntryScreen(
    viewModel: MealEntryViewModel = koinViewModel(),
) {

    val state = viewModel.state.collectAsStateWithLifecycle().value

    MealEntryScreenStateless(
        state = state,
        onBackClicked = viewModel::goBack,
        onAmountChanged = viewModel::amountChanged,
        onMealTypeSelected = viewModel::mealTypeSelected,
        onDateSelected = viewModel::dateSelected,
        onConfirmClicked = viewModel::confirm,
    )
}


@Composable
private fun MealEntryScreenStateless(
    state: MealEntryViewState,
    onBackClicked: () -> Unit = {},
    onAmountChanged: (String) -> Unit = {},
    onMealTypeSelected: (MealType) -> Unit = {},
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
        if (state.isLoading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize(),
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        } else {
            Content(
                item = state.meal,
                mealTypeOptions = state.mealTypeOptions,
                selectedDate = state.selectedDate,
                onAmountChanged = onAmountChanged,
                onMealTypeSelected = onMealTypeSelected,
                onDateSelected = onDateSelected,
                onConfirmClicked = onConfirmClicked,
            )
        }
    }
}

@Composable
private fun Content(
    item: MealViewState,
    mealTypeOptions: Map<String, MealType>,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    onAmountChanged: (String) -> Unit,
    onMealTypeSelected: (MealType) -> Unit,
    onConfirmClicked: () -> Unit,
) {
    var isDatePickerShown by remember { mutableStateOf(false) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier =
            Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            BasicText(
                text = item.name,
                style =
                Typography.h2.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                ),
            )

            VerticalSpacer(12.dp)

            HorizontalDivider()

            VerticalSpacer(32.dp)

            InputRow(
                label = "Amount (servings)",
                initialValue = "1",
                onInputChanged = onAmountChanged,
                keyboardOptions =
                KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number,
                ),
            )

            VerticalSpacer(8.dp)

            DropdownRow(
                label = "Meal",
                options = mealTypeOptions,
                onOptionClicked = onMealTypeSelected,
            )

            VerticalSpacer(8.dp)

            DatePickerRow(
                label = "Date",
                onOpenDateSelection = { isDatePickerShown = true },
                selectedDate = selectedDate,
            )

            HorizontalDivider(
                modifier =
                Modifier.padding(
                    vertical = 32.dp,
                ),
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier =
                Modifier
                    .fillMaxWidth(),
            ) {
                InfoRow(
                    label = "Calories",
                    value = item.calories,
                )
                InfoRow(
                    label = "Carbohydrates",
                    value = item.carbs,
                )
                InfoRow(
                    label = "Protein",
                    value = item.protein,
                )
                InfoRow(
                    label = "Fat",
                    value = item.fat,
                )
            }


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
                onDateSelected = {
                    onDateSelected(it)
                },
                onDismiss = { isDatePickerShown = false },
            )
        }
    }
}

@Composable
private fun DropdownRow(
    label: String,
    options: Map<String, MealType>,
    onOptionClicked: (MealType) -> Unit,
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

        DropdownPicker(
            optionMap = options,
            onOptionClicked = onOptionClicked,
        )
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =
        modifier
            .fillMaxWidth(),
    ) {
        BasicText(
            text = label,
            style =
            Typography.body1.copy(
                color = MaterialTheme.colorScheme.onBackground,
            ),
        )

        Box(
            modifier =
            Modifier
                .weight(1f)
                .height(IntrinsicSize.Min)
                .align(Alignment.Bottom)
                .padding(4.dp),
        ) {
            HorizontalDivider()
        }

        BasicText(
            text = value,
            style =
            Typography.body1.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
            ),
        )
    }
}

@Preview
@Composable
private fun MealEntryPreview() {
    MeallyTheme {
        MealEntryScreenStateless(
            state = MealEntryViewState(
                meal = MealViewState(
                    name = "Grandma's lasagna",
                    calories = "123",
                    carbs = "12",
                    protein = "10",
                    fat = "8",
                ),
                mealTypeOptions = mapOf("Breakfast" to MealType.Empty),
                selectedDate = LocalDate.now(),
                isLoading = false,
            )
        )
    }

}