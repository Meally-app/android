package com.meally.meally.screens.foodInfo

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
import coil3.compose.AsyncImage
import com.meally.meally.common.components.BasicText
import com.meally.meally.common.components.VerticalSpacer
import com.meally.meally.common.navigation.Navigator
import com.meally.meally.common.theme.Typography
import com.meally.meally.screens.food.FoodItemViewState
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.compose.koinInject

@Destination
@Composable
fun FoodInfoScreen(
    foodItem: FoodItemViewState?,
    navigator: Navigator = koinInject(),
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
        AsyncImage(
            model = foodItem?.imageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(0.7f).aspectRatio(1f)
        )

        VerticalSpacer(24.dp)

        Column (
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ){
            if (foodItem != null) {
                BasicText(
                    text = foodItem.name,
                    style = Typography.h2.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )
                FoodDetailRow("Calories", foodItem.calories)
                FoodDetailRow("Carbs", foodItem.carbs)
                FoodDetailRow("Fat", foodItem.fat)
                FoodDetailRow("Protein", foodItem.protein)
            } else {
                BasicText(
                    text = "No food found",
                    style = Typography.h2.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

    }

    BackHandler {
        navigator.goToHome()
    }
}

@Composable
fun FoodDetailRow(name: String, value: String) {
    BasicText(
        text = "$name: $value",
        style = Typography.body1.copy(
            color = MaterialTheme.colorScheme.onBackground,
        ),
    )
}
