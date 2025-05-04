package com.meally.meally.screens.foodInfo.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.meally.meally.common.components.BasicText
import com.meally.meally.common.components.VerticalSpacer
import com.meally.meally.common.theme.Typography
import com.meally.meally.screens.foodInfo.ui.model.FoodInfoViewState
import com.meally.meally.screens.foodInfo.viewModel.FoodInfoViewModel
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.koinViewModel

data class FoodInfoScreenNavArgs(
    val barcode: String,
)

@Destination(navArgsDelegate = FoodInfoScreenNavArgs::class)
@Composable
fun FoodInfoScreen(
    navArgs: FoodInfoScreenNavArgs,
    viewModel: FoodInfoViewModel = koinViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    FoodInfoScreenStateless(
        state = state,
        barcode = navArgs.barcode,
    )

    BackHandler {
        viewModel.goBack()
    }
}

@Composable
fun FoodInfoScreenStateless(
    state: FoodInfoViewState,
    barcode: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier =
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
    ) {
        when (state) {
            is FoodInfoViewState.Loaded -> {
                AsyncImage(
                    model = state.foodItem.imageUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(0.7f).aspectRatio(1f),
                )

                VerticalSpacer(24.dp)

                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    BasicText(
                        text = state.foodItem.name,
                        style =
                            Typography.h2.copy(
                                color = MaterialTheme.colorScheme.onBackground,
                                textAlign = TextAlign.Center,
                            ),
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    )
                    FoodDetailRow("Calories", state.foodItem.calories)
                    FoodDetailRow("Carbs", state.foodItem.carbs)
                    FoodDetailRow("Fat", state.foodItem.fat)
                    FoodDetailRow("Protein", state.foodItem.protein)
                }
            }
            FoodInfoViewState.Loading -> {
                BasicText(
                    text = "Loading",
                    style =
                        Typography.h2.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center,
                        ),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            FoodInfoViewState.Error -> {
                BasicText(
                    text = "No food found for barcode $barcode",
                    style =
                        Typography.h2.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center,
                        ),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
fun FoodDetailRow(
    name: String,
    value: String,
) {
    BasicText(
        text = "$name: $value",
        style =
            Typography.body1.copy(
                color = MaterialTheme.colorScheme.onBackground,
            ),
    )
}
