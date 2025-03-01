package com.meally.meally.ui.food

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.meally.domain.barcode.Barcode
import com.meally.meally.common.barcodeAnalyzer.BarcodeAnalyzer
import com.meally.meally.common.barcodeAnalyzer.BarcodeScanView
import com.meally.meally.common.components.VerticalSpacer
import com.meally.meally.common.navigation.Navigator
import com.meally.meally.ui.food.destinations.OtherScreenDestination
import com.meally.meally.ui.theme.Typography
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Destination(start = true)
@Composable
fun FoodScreen(
    navigator: Navigator = koinInject(),
    foodViewModel: FoodViewModel = koinViewModel(),
) {
    val state = foodViewModel.viewState.collectAsStateWithLifecycle().value

    val barcodeAnalyzer =
        remember {
            BarcodeAnalyzer(
                process = { Barcode(it ?: "") },
                onSuccess = foodViewModel::onBarcodeChanged,
                onFailure = {},
            )
        }
    FoodScreenStateless(
        state = state,
        barcodeScannerView = {
            BarcodeScanView(
                barcodeAnalyzer = barcodeAnalyzer,
                modifier = Modifier,
            )
        },
        onButtonClicked = { navigator.navigate(OtherScreenDestination(2)) },
    )
}

@Composable
fun FoodScreenStateless(
    state: FoodViewState,
    barcodeScannerView: @Composable () -> Unit = {},
    onButtonClicked: () -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().background(Color.White),
    ) {
        if (state.isLoadingInitialData) {
            CircularProgressIndicator()
        } else {
            barcodeScannerView()
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                VerticalSpacer(24.dp)
                Text(
                    text = "Fetching food from repository",
                    style = Typography.headlineMedium,
                )
                VerticalSpacer(24.dp)
                Text(text = "Food is ${state.food}")
                VerticalSpacer(24.dp)
                Text(text = state.barcode)
                VerticalSpacer(24.dp)
                Button(
                    onClick = onButtonClicked,
                ) {
                    Text(
                        text = "Other screen",
                        style = Typography.labelLarge,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun FoodScreenPreview() {
    FoodScreenStateless(
        state =
            FoodViewState(
                isLoadingInitialData = false,
                food = FoodItemViewState(1L, "Protein Bar"),
                barcode = "123456789",
            ),
    )
}
