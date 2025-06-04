package com.meally.meally.screens.recentFood.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
import com.meally.meally.common.components.HorizontalSpacer
import com.meally.meally.common.components.VerticalSpacer
import com.meally.meally.common.components.focusClearer
import com.meally.meally.common.theme.MeallyTheme
import com.meally.meally.common.theme.Typography
import com.meally.meally.screens.recentFood.ui.model.RecentFoodItem
import com.meally.meally.screens.recentFood.ui.model.RecentFoodViewState
import com.meally.meally.screens.recentFood.viewModel.RecentFoodViewModel
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun RecentFoodScreen(
    viewModel: RecentFoodViewModel = koinViewModel(),
) {
    val state = viewModel.viewState.collectAsStateWithLifecycle().value

    RecentFoodScreenStateless(
        state = state,
        onItemClicked = viewModel::itemClicked,
        onBackClicked = viewModel::goBack,
    )
}

@Composable
private fun RecentFoodScreenStateless(
    state: RecentFoodViewState,
    onItemClicked: (RecentFoodItem) -> Unit = {},
    onBackClicked: () -> Unit = {},
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
            onItemClicked = onItemClicked,
        )
    }
}

@Composable
fun Content(
    state: RecentFoodViewState,
    onItemClicked: (RecentFoodItem) -> Unit,
) {

    LazyColumn (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .focusClearer(LocalFocusManager.current)
            .padding(horizontal = 24.dp)
    ) {

        item {
            VerticalSpacer(16.dp)
            BasicText(
                text = "Recently entered",
                style = Typography.h1.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                ),
                modifier = Modifier.fillMaxWidth(),
            )
            VerticalSpacer(16.dp)
        }

        when {
            state.isLoading -> {
                item {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
            state.items.isEmpty() -> {
                item {
                    BasicText(
                        text = "You don't have any recent food.",
                        style = Typography.h3.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }
            else -> {
                items(state.items) { item ->
                    RecentFoodItemView(
                        item = item,
                        onClick = onItemClicked,
                    )
                }
            }
        }
    }

}

@Composable
private fun RecentFoodItemView(
    item: RecentFoodItem,
    onClick: (RecentFoodItem) -> Unit,
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
private fun RecentFoodPreview() {
    MeallyTheme {
        RecentFoodScreenStateless(
            state = RecentFoodViewState(
                isLoading = false,
                items = listOf(
                    RecentFoodItem(
                        name = "Banana",
                        date = "Today",
                        barcode = "",
                    ),
                    RecentFoodItem(
                        name = "Banana",
                        date = "Today",
                        barcode = "",
                    ),
                    RecentFoodItem(
                        name = "Banana",
                        date = "Today",
                        barcode = "",
                    ),
                )
            )
        )
    }

}