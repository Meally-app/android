package com.meally.meally.screens.foodEntry.ui

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.width
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.meally.domain.mealType.MealType
import com.meally.meally.R
import com.meally.meally.common.components.AppBar
import com.meally.meally.common.components.BasicButton
import com.meally.meally.common.components.BasicText
import com.meally.meally.common.components.BasicTextField
import com.meally.meally.common.components.datePicker.DatePickerModal
import com.meally.meally.common.components.DropdownPicker
import com.meally.meally.common.components.HorizontalSpacer
import com.meally.meally.common.components.VerticalSpacer
import com.meally.meally.common.components.datePicker.DatePickerInput
import com.meally.meally.common.components.focusClearer
import com.meally.meally.common.theme.MeallyTheme
import com.meally.meally.common.theme.Typography
import com.meally.meally.screens.foodEntry.viewModel.FoodEntryViewModel
import com.meally.meally.common.food.viewState.FoodInfoViewState
import com.meally.meally.common.food.viewState.FoodItemViewState
import com.meally.meally.common.time.util.toEpochMillis
import com.meally.meally.screens.foodInfo.ui.model.FoodEntryViewState
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.koinViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

data class FoodEntryScreenNavArgs(
    val barcode: String,
)

@Destination(navArgsDelegate = FoodEntryScreenNavArgs::class)
@Composable
fun FoodEntryScreen(viewModel: FoodEntryViewModel = koinViewModel()) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    FoodEntryScreenStateless(
        state = state,
        onBackClicked = viewModel::goBack,
        onAmountChanged = viewModel::amountChanged,
        onMealTypeSelected = viewModel::mealTypeSelected,
        onDateSelected = viewModel::dateSelected,
        onConfirmClicked = viewModel::confirm,
    )

    BackHandler {
        viewModel.goBack()
    }
}

@Composable
fun FoodEntryScreenStateless(
    state: FoodEntryViewState,
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

        when (state.foodInfoViewState) {
            FoodInfoViewState.Error -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                ) {
                    BasicText(
                        "There has been an error.",
                        style =
                            Typography.body1.copy(
                                color = MaterialTheme.colorScheme.onBackground,
                                textAlign = TextAlign.Center,
                            ),
                    )
                }
            }
            FoodInfoViewState.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
            is FoodInfoViewState.Loaded -> {
                Content(
                    item = state.foodInfoViewState.foodItem,
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
}

@Composable
private fun Content(
    item: FoodItemViewState,
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
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier =
                Modifier
                    .height(80.dp)
                    .fillMaxWidth(),
            ) {
                BasicText(
                    text = item.name,
                    style =
                    Typography.h2.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                    ),
                )

                HorizontalSpacer(24.dp)

                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = null,
                    modifier =
                    Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f),
                )
            }

            HorizontalDivider()

            VerticalSpacer(32.dp)

            InputRow(
                label = "Amount (${item.unitOfMeasurement}):",
                initialValue = "100",
                onInputChanged = onAmountChanged,
                keyboardOptions =
                KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.NumberPassword,
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

@Composable
private fun InputRow(
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

@Preview
@Composable
private fun LoadingPreview() {
    MeallyTheme {
        FoodEntryScreenStateless(
            state = FoodEntryViewState(
                foodInfoViewState = FoodInfoViewState.Loading,
                mealTypeOptions = mapOf(),
                selectedDate = LocalDate.now(),
            )
        )
    }
}

@Preview
@Composable
private fun LoadedPreview() {
    MeallyTheme {
        FoodEntryScreenStateless(
            state = FoodEntryViewState(
                foodInfoViewState = FoodInfoViewState.Loaded(
                    foodItem =
                    FoodItemViewState(
                        name = "Food",
                        imageUrl = "",
                        calories = "123.11",
                        carbs = "12.1",
                        protein = "8.4",
                        fat = "22.0",
                        unitOfMeasurement = "g",
                    ),
                ),
                mealTypeOptions = mapOf("Breakfast" to MealType("breakfast", 1)),
                selectedDate = LocalDate.now(),
            )

        )
    }
}
