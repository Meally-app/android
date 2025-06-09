package com.meally.meally.screens.userMeals.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.meally.domain.meal.Meal
import com.meally.domain.meal.MealVisibility
import com.meally.domain.meal.calories
import com.meally.domain.user.User
import com.meally.meally.R
import com.meally.meally.common.components.AppBar
import com.meally.meally.common.components.BasicText
import com.meally.meally.common.components.VerticalSpacer
import com.meally.meally.common.theme.MeallyTheme
import com.meally.meally.common.theme.Typography
import com.meally.meally.screens.userMeals.viewModel.UserMealsViewModel
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun UserMealsScreen(
    viewModel: UserMealsViewModel = koinViewModel(),
) {
    val state = viewModel.viewState.collectAsStateWithLifecycle().value

    UserMealsScreenStateless(
        state = state,
        onBackClicked = viewModel::goBack,
        onAddMealClicked = viewModel::createMeal,
        onMealClicked = viewModel::mealClicked,
    )
}

@Composable
fun UserMealsScreenStateless(
    state: List<Meal>,
    onBackClicked: () -> Unit = {},
    onAddMealClicked: () -> Unit = {},
    onMealClicked: (Meal) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        AppBar(
            leadingIconResource = R.drawable.ic_back,
            onLeadingIconClicked = onBackClicked,
            trailingIconResource = R.drawable.ic_plus,
            onTrailingIconClicked = onAddMealClicked,
            middleSection = {
                BasicText(
                    text = "My meals",
                    style = Typography.h3.copy(color = MaterialTheme.colorScheme.onBackground)
                )
            }
        )

        LazyColumn (
            contentPadding = PaddingValues(vertical = 24.dp),
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            items(state, key = { it.id }) {
                MealView(
                    meal = it,
                    onMealClicked = { onMealClicked(it) },
                    modifier = Modifier.animateItem(),
                )
            }
        }
    }
}

@Composable
private fun MealView(
    meal: Meal,
    onMealClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .clickable { onMealClicked() }
            .padding(16.dp)
    ){
        Column {
            BasicText(
                text = meal.name,
                style = Typography.h3.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            VerticalSpacer(4.dp)
            BasicText(
                text = meal.visibility.name,
                style = Typography.body2.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }

        BasicText(
            text = "${meal.calories} kcal",
            style = Typography.body1.copy(
                color = MaterialTheme.colorScheme.onBackground
            )
        )
    }
}

@Preview
@Composable
private fun UserMealsPreview() {
    MeallyTheme {
        UserMealsScreenStateless(
            state = listOf(
                Meal(
                    id = "a",
                    name = "Grandma's lasagna",
                    user = User("", "", ""),
                    visibility = MealVisibility.PUBLIC,
                    foodInMeal = listOf(),
                )
            )
        )
    }

}