package com.meally.meally.screens.createMeal.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.meally.domain.food.Food
import com.meally.domain.meal.FoodInMeal
import com.meally.domain.meal.Meal
import com.meally.domain.meal.MealVisibility
import com.meally.domain.meal.calories
import com.meally.domain.user.User
import com.meally.meally.R
import com.meally.meally.common.components.AppBar
import com.meally.meally.common.components.BasicButton
import com.meally.meally.common.components.BasicText
import com.meally.meally.common.components.BasicTextField
import com.meally.meally.common.components.DropdownPicker
import com.meally.meally.common.components.HorizontalSpacer
import com.meally.meally.common.components.OutlinedBasicButton
import com.meally.meally.common.components.VerticalSpacer
import com.meally.meally.common.components.focusClearer
import com.meally.meally.common.theme.MeallyTheme
import com.meally.meally.common.theme.Typography
import com.meally.meally.screens.createMeal.viewModel.CreateMealViewModel
import com.meally.meally.screens.destinations.SearchFoodScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import org.koin.androidx.compose.koinViewModel

data class CreateMealScreenNavArgs(
    val mealId: String? = null,
)

@Destination(navArgsDelegate = CreateMealScreenNavArgs::class)
@Composable
fun CreateMealScreen(
    resultRecipient: ResultRecipient<SearchFoodScreenDestination, String>,
    viewModel: CreateMealViewModel = koinViewModel(),
) {
    resultRecipient.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {}
            is NavResult.Value -> {
                viewModel.addFood(result.value)
            }
        }
    }

    val state = viewModel.viewState.collectAsStateWithLifecycle().value

    CreateMealScreenStateless(
        meal = state,
        onBackClicked = viewModel::goBack,
        onSearchFood = viewModel::searchFood,
        onMealChanged = viewModel::mealChanged,
        onConfirmClicked = viewModel::confirm
    )
}

@Composable
private fun CreateMealScreenStateless(
    meal: Meal,
    onMealChanged: (Meal) -> Unit = {},
    onBackClicked: () -> Unit = {},
    onSearchFood: () -> Unit = {},
    onConfirmClicked: () -> Unit = {},
) {

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .focusClearer(LocalFocusManager.current)
    ){
        AppBar(
            leadingIconResource = R.drawable.ic_back,
            onLeadingIconClicked = onBackClicked,
        )
        Content(
            meal = meal,
            onMealChanged = onMealChanged,
            onSearchFood = onSearchFood,
            onConfirmClicked = onConfirmClicked,
        )
    }

}

@Composable
private fun ColumnScope.Content(
    meal: Meal,
    onMealChanged: (Meal) -> Unit,
    onSearchFood: () -> Unit,
    onConfirmClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn (
        contentPadding = PaddingValues(vertical = 24.dp),
        modifier = modifier
            .fillMaxWidth()
            .weight(1f)
            .padding(horizontal = 24.dp)
    ) {

        item ("input_meal_name"){
            BasicTextField(
                text = meal.name,
                onTextChanged = { onMealChanged(meal.copy(name = it)) },
                label = {
                    BasicText(
                        text = "Meal name",
                        style = Typography.body2
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item ("input_meal_visibility") {
            MealVisibilityPicker(
                onOptionClicked = { onMealChanged(meal.copy(visibility = it)) },
                modifier = Modifier.padding(vertical = 12.dp)
            )
        }

        item ("search_food_button") {
            OutlinedBasicButton(
                onClick = onSearchFood,
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Icon(
                        painter = painterResource(R.drawable.ic_plus),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.onBackground,
                    )
                    HorizontalSpacer(8.dp)
                    BasicText(
                        text = "Add food",
                        style = Typography.body2.copy(color = MaterialTheme.colorScheme.onBackground)
                    )
                }
            }
            VerticalSpacer(12.dp)
        }

        items(meal.foodInMeal, key = { it.food.id }) { foodInMeal ->
            FoodElement(
                foodInMeal = foodInMeal,
                onAmountChanged = { amount ->
                    onMealChanged(
                        meal.copy(
                            foodInMeal = meal.foodInMeal.map { if (foodInMeal.food == it.food) foodInMeal.copy(amount = amount.toDouble()) else it }
                        )
                    )
                },
                modifier = Modifier.animateItem().padding(vertical = 8.dp)
            )
        }

        if (meal.foodInMeal.isEmpty()) {
            item ("empty_food_item"){
                BasicText(
                    text = "Add some food to your meal!",
                    style = Typography.h3.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp),
                )
            }
        }
    }

    VerticalSpacer(12.dp)

    BasicButton(
        text = "Confirm",
        onClick = onConfirmClicked,
        modifier = Modifier.height(56.dp).fillMaxWidth().padding(horizontal = 24.dp)
    )

    VerticalSpacer(12.dp)

}

@Composable
private fun FoodElement(
    foodInMeal: FoodInMeal,
    onAmountChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ){
        Column (
            modifier = Modifier.weight(1f)
        ){
            BasicText(
                text = foodInMeal.food.name,
                style = Typography.h3.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            VerticalSpacer(4.dp)
            BasicText(
                text = "${foodInMeal.calories} kcal",
                style = Typography.body1.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }

        Row (
            verticalAlignment = Alignment.CenterVertically,
        ){
            BasicTextField(
                text = foodInMeal.amount.toInt().toString(),
                onTextChanged = {
                    onAmountChanged(it.toIntOrNull() ?: 0)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Done,
                ),
                textStyle = Typography.body2.copy(textAlign = TextAlign.Center),
                modifier = Modifier.width(72.dp)
            )
            HorizontalSpacer(4.dp)
            BasicText(
                text = foodInMeal.food.unitOfMeasurement.abbreviation,
                style = Typography.body2.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }

    }
}

@Composable
private fun MealVisibilityPicker(
    onOptionClicked: (MealVisibility) -> Unit,
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
            text = "Meal visibility",
            style = Typography.body1.copy(
                color = MaterialTheme.colorScheme.onBackground,
            ),
        )

        HorizontalSpacer(24.dp)

        DropdownPicker(
            optionMap = MealVisibility.entries.filter { it != MealVisibility.UNKNOWN }.associateBy { it.name.lowercase().replaceFirstChar { it.uppercase() } },
            onOptionClicked = onOptionClicked,
        )
    }
}

@Preview
@Composable
private fun CreateMealEmptyPreview() {
    MeallyTheme {
        CreateMealScreenStateless(
            meal = Meal(
                id = "",
                name = "Test meal",
                user = User.Empty,
                visibility = MealVisibility.PUBLIC,
                foodInMeal = listOf(),
                isLiked = false,
            )
        )
    }
}

@Preview
@Composable
private fun CreateMealPreview() {
    MeallyTheme {
        CreateMealScreenStateless(
            meal = Meal(
                id = "",
                name = "Test meal",
                user = User.Empty,
                visibility = MealVisibility.PUBLIC,
                isLiked = false,
                foodInMeal = listOf(
                    FoodInMeal(
                        food = Food.Empty.copy(
                            id = "a",
                            name = "Pasta",
                            calories = 200.00
                        ),
                        amount = 300.0,
                    ),
                    FoodInMeal(
                        food = Food.Empty.copy(
                            id = "b",
                            name = "Minced Beef",
                            calories = 350.00
                        ),
                        amount = 450.0,
                    ),
                    FoodInMeal(
                        food = Food.Empty.copy(
                            id = "c",
                            name = "Tomato sauce asd dsak oapsd odsk oskd ",
                            calories = 70.50,
                            unitOfMeasurement = Food.UnitOfMeasurement.MILLILITERS,
                        ),
                        amount = 250.0,
                    ),
                ),
            )
        )
    }
}
