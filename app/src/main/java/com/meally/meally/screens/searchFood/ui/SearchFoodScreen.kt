package com.meally.meally.screens.searchFood.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.meally.meally.R
import com.meally.meally.common.components.AppBar
import com.meally.meally.common.components.BasicText
import com.meally.meally.common.components.BasicTextField
import com.meally.meally.common.components.HorizontalSpacer
import com.meally.meally.common.components.VerticalSpacer
import com.meally.meally.common.components.focusClearer
import com.meally.meally.common.theme.MeallyTheme
import com.meally.meally.common.theme.Typography
import com.meally.meally.screens.searchFood.ui.model.SearchFoodItem
import com.meally.meally.screens.searchFood.ui.model.SearchFoodViewState
import com.meally.meally.screens.searchFood.viewModel.SearchFoodViewModel
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun SearchFoodScreen(
    viewModel: SearchFoodViewModel = koinViewModel(),
) {
    val state = viewModel.viewState.collectAsStateWithLifecycle().value

    SearchFoodScreenStateless(
        state = state,
        onBackClicked = viewModel::goBack,
        onInputChanged = viewModel::updateUserInput,
        onItemClicked = viewModel::itemClicked,
    )
}

@Composable
private fun SearchFoodScreenStateless(
    state: SearchFoodViewState,
    onBackClicked: () -> Unit = {},
    onInputChanged: (String) -> Unit = {},
    onItemClicked: (SearchFoodItem) -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        AppBar(
            leadingIconResource = R.drawable.ic_back,
            onLeadingIconClicked = onBackClicked,
        )

        Content(
            state = state,
            onInputChanged = onInputChanged,
            onItemClicked = onItemClicked,
        )
    }
}

@Composable
private fun Content(
    state: SearchFoodViewState,
    onInputChanged: (String) -> Unit,
    onItemClicked: (SearchFoodItem) -> Unit,
) {

    var text by remember { mutableStateOf("") }
    LazyColumn (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .focusClearer(LocalFocusManager.current)
            .padding(horizontal = 24.dp)
    ) {
        item {
            VerticalSpacer(16.dp)
            BasicTextField(
                text = text,
                onTextChanged = {
                    text = it
                    onInputChanged(it)
                },
                label = {
                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = null,
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            VerticalSpacer(32.dp)
        }
        when {
            state.isLoading -> {
                item {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
            state.food.isEmpty() -> {
                item {
                    BasicText(
                        text = "Search for some food.",
                        style = Typography.h3.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }
        }
        if (state.isLoading) {

        } else {
            items(state.food) { food ->
                FoodItemView(
                    item = food,
                    onClick = onItemClicked,
                )
            }
        }
    }
}

@Composable
private fun FoodItemView(
    item: SearchFoodItem,
    onClick: (SearchFoodItem) -> Unit,
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(item) }
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ){
        VerticalSpacer(16.dp)
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
        ){
            BasicText(
                text = item.name,
                style = Typography.body2.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                )
            )
            HorizontalSpacer(16.dp)
            Icon(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.rotate(180f)
            )
        }
        VerticalSpacer(16.dp)
        HorizontalDivider()
    }
}

@Preview
@Composable
private fun SearchFoodPreview() {
    MeallyTheme {
        SearchFoodScreenStateless(
            state = SearchFoodViewState(
                isLoading = false,
                food = listOf(
                    SearchFoodItem(
                        name = "Banana",
                        barcode = "",
                        calories = "100",
                    ),
                    SearchFoodItem(
                        name = "Hidra",
                        barcode = "",
                        calories = "100",
                    ),
                    SearchFoodItem(
                        name = "Cookies",
                        barcode = "",
                        calories = "100",
                    ),
                )
            )
        )
    }
}

@Preview
@Composable
private fun SearchFoodLoadingPreview() {
    MeallyTheme {
        SearchFoodScreenStateless(
            state = SearchFoodViewState(
                isLoading = true,
                food = listOf(),
            )
        )
    }
}

@Preview
@Composable
private fun SearchFoodEmptyPreview() {
    MeallyTheme {
        SearchFoodScreenStateless(
            state = SearchFoodViewState(
                isLoading = false,
                food = listOf(),
            )
        )
    }
}