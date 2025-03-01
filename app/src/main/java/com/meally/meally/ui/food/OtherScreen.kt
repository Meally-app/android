package com.meally.meally.ui.food

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.meally.meally.common.navigation.Navigator
import com.meally.meally.ui.food.destinations.FoodScreenDestination
import com.meally.meally.ui.theme.Typography
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.compose.koinInject

@Destination
@Composable
fun OtherScreen(
    number: Int,
    navigator: Navigator = koinInject(),
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        Button(
            onClick = { navigator.navigate(FoodScreenDestination) },
        ) {
            Text(
                text = "Barcode scan $number",
                style = Typography.labelLarge,
                textAlign = TextAlign.Center,
            )
        }
    }
}
