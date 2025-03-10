package com.meally.meally.screens.foodInfo

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.meally.meally.common.components.BasicText
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
                .padding(horizontal = 24.dp),
    ) {
        BasicText(
            text = foodItem?.toString() ?: "No food item found for this barcode",
            style =
                Typography.body1.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                ),
        )
    }

    BackHandler {
        navigator.goToHome()
    }
}
