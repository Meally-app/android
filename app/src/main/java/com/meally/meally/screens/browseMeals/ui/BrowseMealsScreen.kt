package com.meally.meally.screens.browseMeals.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.meally.domain.meal.BrowseMeal
import com.meally.domain.user.User
import com.meally.meally.R
import com.meally.meally.common.components.BasicText
import com.meally.meally.common.components.BasicTextField
import com.meally.meally.common.components.HorizontalSpacer
import com.meally.meally.common.components.VerticalSpacer
import com.meally.meally.common.components.focusClearer
import com.meally.meally.common.theme.MeallyTheme
import com.meally.meally.common.theme.Typography
import com.meally.meally.screens.browseMeals.state.BrowseMealsSearchState
import com.meally.meally.screens.browseMeals.ui.model.BrowseMealsViewState
import com.meally.meally.screens.browseMeals.viewModel.BrowseMealsViewModel
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun BrowseMealsScreen(
    viewModel: BrowseMealsViewModel = koinViewModel(),
) {

    val state = viewModel.viewState.collectAsStateWithLifecycle().value

    BrowseMealsScreenStateless(
        state = state,
        onSearchTermChanged = viewModel::searchTermChanged,
        onCaloriesMinChanged = viewModel::caloriesMinChanged,
        onCaloriesMaxChanged = viewModel::caloriesMaxChanged,
        onMealClicked = viewModel::mealClicked,
    )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BrowseMealsScreenStateless(
    state: BrowseMealsViewState,
    onSearchTermChanged: (String) -> Unit = {},
    onCaloriesMinChanged: (Double) -> Unit = {},
    onCaloriesMaxChanged: (Double) -> Unit = {},
    onMealClicked: (BrowseMeal) -> Unit = {},
) {

    LazyColumn (
        contentPadding = PaddingValues(bottom = 24.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .focusClearer(LocalFocusManager.current)
            .padding(24.dp)
    ) {
        stickyHeader {
            Filter(
                searchState = state.searchState,
                onSearchTermChanged = onSearchTermChanged,
                onCaloriesMinChanged = onCaloriesMinChanged,
                onCaloriesMaxChanged = onCaloriesMaxChanged,
            )
        }

        items(state.meals, key = { it.name + it.calories}) {
            MealView(
                meal = it,
                onMealClicked = { onMealClicked(it) },
                modifier = Modifier.padding(vertical = 12.dp).animateItem()
            )
        }
    }

}

@Composable
private fun Filter(
    searchState: BrowseMealsSearchState,
    onSearchTermChanged: (String) -> Unit,
    onCaloriesMinChanged: (Double) -> Unit,
    onCaloriesMaxChanged: (Double) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 12.dp),
    ){
        BasicTextField(
            text = searchState.searchTerm,
            onTextChanged = onSearchTermChanged,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = null,
                )
            },
        )
        VerticalSpacer(8.dp)
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            BasicTextField(
                text = searchState.caloriesMin.toString(),
                onTextChanged = { onCaloriesMinChanged(it.toDouble()) },
                label = {
                    BasicText(
                        text = "Min. calories",
                        style = Typography.body2,
                    )
                },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number,
                ),
            )
            HorizontalSpacer(12.dp)
            BasicTextField(
                text = searchState.caloriesMax.toString(),
                onTextChanged = { onCaloriesMaxChanged(it.toDouble()) },
                label = {
                    BasicText(
                        text = "Max. calories",
                        style = Typography.body2,
                    )
                },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number,
                ),
            )
        }
        VerticalSpacer(12.dp)
        HorizontalDivider(
            Modifier.height(2.dp)
        )
    }
}

@Composable
private fun MealView(
    meal: BrowseMeal,
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
                text = meal.user.username,
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
private fun BrowseMealsPreview() {
    MeallyTheme {
        BrowseMealsScreenStateless(
            state = BrowseMealsViewState(
                isLoading = false,
                meals = listOf(
                    BrowseMeal(
                        id = "aa",
                        name = "Grandma's lasagna",
                        calories = 520.1,
                        user = User(
                            id = "",
                            email = "",
                            username = "Username"
                        ),
                        isLiked = false,
                    ),
                    BrowseMeal(
                        id = "b",
                        name = "Grandma's lasagna",
                        calories = 520.1,
                        user = User(
                            id = "",
                            email = "",
                            username = "Username"
                        ),
                        isLiked = false,
                    ),
                    BrowseMeal(
                        id = "c",
                        name = "Grandma's lasagna",
                        calories = 520.1,
                        user = User(
                            id = "",
                            email = "",
                            username = "Username"
                        ),
                        isLiked = false,
                    ),
                ),
                searchState = BrowseMealsSearchState(
                    searchTerm = "aaa",
                    caloriesMin = 0.0,
                    caloriesMax = 5000.0,
                )
            )
        )
    }

}