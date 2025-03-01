package com.meally.meally.ui.food

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.meally.domain.barcode.Barcode
import com.meally.meally.common.barcodeAnalyzer.BarcodeAnalyzer
import com.meally.meally.common.barcodeAnalyzer.BarcodeScanView
import com.meally.meally.common.components.VerticalSpacer
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FoodScreen(foodViewModel: FoodViewModel = koinViewModel()) {
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
            BarcodeScanView(barcodeAnalyzer = barcodeAnalyzer, modifier = Modifier)
        },
    )
}

@Composable
fun FoodScreenStateless(
    state: FoodViewState,
    barcodeScannerView: @Composable () -> Unit,
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
                Text(text = "Fetching food from repository")
                VerticalSpacer(24.dp)
                Text(text = "Food is ${state.food}")
                VerticalSpacer(24.dp)
                Text(text = state.barcode)
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
        barcodeScannerView = {
            Box(Modifier.fillMaxWidth().aspectRatio(16f / 9).background(Color.Black))
        },
    )
}
