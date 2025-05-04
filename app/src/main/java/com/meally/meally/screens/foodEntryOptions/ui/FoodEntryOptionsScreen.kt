package com.meally.meally.screens.foodEntryOptions.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.meally.meally.R
import com.meally.meally.common.components.AppBar
import com.meally.meally.common.components.BasicText
import com.meally.meally.common.components.VerticalSpacer
import com.meally.meally.common.theme.MeallyTheme
import com.meally.meally.common.theme.Typography
import com.meally.meally.screens.foodEntryOptions.mapper.foodEntryOptionsMapper
import com.meally.meally.screens.foodEntryOptions.ui.model.FoodEntryOption
import com.meally.meally.screens.foodEntryOptions.ui.model.FoodEntryOptionsViewState
import com.meally.meally.screens.foodEntryOptions.viewModel.FoodEntryOptionsViewModel
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun FoodEntryOptionsScreen(viewModel: FoodEntryOptionsViewModel = koinViewModel()) {
    val state = foodEntryOptionsMapper()

    FoodEntryOptionsScreenStateless(
        state = state,
        onOptionClicked = viewModel::optionClicked,
        onBackClicked = viewModel::goBack,
    )
}

@Composable
fun FoodEntryOptionsScreenStateless(
    state: FoodEntryOptionsViewState,
    onOptionClicked: (FoodEntryOption) -> Unit = {},
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
            onOptionClicked = onOptionClicked,
        )
    }
}

@Composable
private fun Content(
    state: FoodEntryOptionsViewState,
    onOptionClicked: (FoodEntryOption) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
    ) {
        state.options.forEach {
            EntryOptionView(
                option = it,
                onOptionClicked = onOptionClicked,
            )
        }
    }
}

@Composable
fun EntryOptionView(
    option: FoodEntryOption,
    onOptionClicked: (FoodEntryOption) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(24.dp))
                .clip(RoundedCornerShape(24.dp))
                .clickable { onOptionClicked(option) }
                .padding(32.dp),
    ) {
        Icon(
            painter = painterResource(option.icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.size(48.dp),
        )
        VerticalSpacer(16.dp)
        BasicText(
            text = option.text,
            style =
                Typography.h2.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                ),
        )
    }
}

@Preview
@Composable
private fun FoodEntryOptionsPreview() {
    MeallyTheme {
        FoodEntryOptionsScreenStateless(
            state = foodEntryOptionsMapper(),
        )
    }
}
