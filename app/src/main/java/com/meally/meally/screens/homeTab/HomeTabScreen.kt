package com.meally.meally.screens.homeTab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.meally.meally.common.components.BasicButton
import com.meally.meally.common.navigation.Navigator
import com.meally.meally.common.theme.MeallyTheme
import com.meally.meally.screens.destinations.FoodScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.compose.koinInject

@Destination(start = true)
@Composable
fun HomeTabScreen(navigator: Navigator = koinInject()) {
    HomeTabScreenStateless(
        onButtonClicked = {
            navigator.navigate(FoodScreenDestination)
        },
    )
}

@Composable
fun HomeTabScreenStateless(onButtonClicked: () -> Unit = {}) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
    ) {
        BasicButton(
            text = "Barcode Scan",
            onClick = onButtonClicked,
        )
    }
}

@Preview
@Composable
private fun HomeTabPreview() {
    MeallyTheme {
        HomeTabScreenStateless()
    }
}
