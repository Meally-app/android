package com.meally.meally.screens.mealDetails.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
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
import com.meally.meally.common.components.BasicText
import com.meally.meally.common.components.HorizontalSpacer
import com.meally.meally.common.components.OutlinedBasicButton
import com.meally.meally.common.components.VerticalSpacer
import com.meally.meally.common.theme.MeallyTheme
import com.meally.meally.common.theme.Typography
import com.meally.meally.screens.mealDetails.viewModel.MealDetailsViewModel
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.koinViewModel

data class MealDetailsScreenNavArgs(
    val mealId: String,
)

@Destination(navArgsDelegate = MealDetailsScreenNavArgs::class)
@Composable
fun MealDetailsScreen(
    viewModel: MealDetailsViewModel = koinViewModel(),
) {

    val state = viewModel.viewState.collectAsStateWithLifecycle().value

    MealDetailsScreenStateless(
        meal = state,
        onBackClicked = viewModel::goBack,
        onAddToDiary = viewModel::addToDiary,
    )

}

@Composable
private fun MealDetailsScreenStateless(
    meal: Meal?,
    onBackClicked: () -> Unit = {},
    onAddToDiary: () -> Unit = {},
) {

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        AppBar(
            leadingIconResource = R.drawable.ic_back,
            onLeadingIconClicked = onBackClicked,
        )

        if (meal != null) {
            Content(
                meal = meal,
                onAddToDiary = onAddToDiary,
            )
        } else {
            Box (
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize(),
            ){
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }
    }

}

@Composable
private fun Content(
    meal: Meal,
    onAddToDiary: () -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 12.dp),
        modifier = Modifier.padding(horizontal = 24.dp)
    ) {
        item ("item_name"){
            TextElement("Name", meal.name, Modifier.animateItem())
        }

        item ("item_user") {
            TextElement("Made by", meal.user.username, Modifier.animateItem())
        }

        item ("item_calories") {
            TextElement("Total calories", "${meal.calories.toInt()} kcal", Modifier.animateItem())
        }

        item ("add_button") {
            OutlinedBasicButton(
                onClick = onAddToDiary,
                modifier = Modifier.padding(vertical = 8.dp),
            ) {
                Row {
                    Icon(
                        painter = painterResource(R.drawable.ic_plus),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.onBackground,
                    )
                    HorizontalSpacer(8.dp)
                    BasicText(
                        text = "Add to diary",
                        style = Typography.body2.copy(color = MaterialTheme.colorScheme.onBackground)
                    )
                }
            }
        }

        items(meal.foodInMeal, key = { it.food.id + it.amount }) {
            FoodElement(
                foodInMeal = it,
                modifier = Modifier.animateItem().padding(vertical = 12.dp),
            )
        }

    }
}

@Composable
private fun TextElement(
    key: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ){

        BasicText(
            text = key,
            style = Typography.h2.copy(color = MaterialTheme.colorScheme.onBackground)
        )

        VerticalSpacer(4.dp)

        BasicText(
            text = value,
            style = Typography.body1.copy(color = MaterialTheme.colorScheme.onBackground)
        )
    }
}

@Composable
private fun FoodElement(
    foodInMeal: FoodInMeal,
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
        Column {
            BasicText(
                text = foodInMeal.food.name,
                style = Typography.h3.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            VerticalSpacer(4.dp)
            BasicText(
                text = "${foodInMeal.amount} ${foodInMeal.food.unitOfMeasurement.abbreviation}",
                style = Typography.body2.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }

        BasicText(
            text = "${foodInMeal.amount * foodInMeal.food.calories / 100} kcal",
            style = Typography.body1.copy(
                color = MaterialTheme.colorScheme.onBackground
            )
        )
    }
}

@Preview
@Composable
private fun MealDetailsPreview() {
    MeallyTheme {
        MealDetailsScreenStateless(
            meal = Meal(
                id = "a",
                name = "Grandma's lasagna",
                user = User("", "", "Username"),
                visibility = MealVisibility.PUBLIC,
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
                            name = "Tomato sauce",
                            calories = 70.50,
                            unitOfMeasurement = Food.UnitOfMeasurement.MILLILITERS,
                        ),
                        amount = 250.0,
                    )
                ),
            )
        )
    }

}