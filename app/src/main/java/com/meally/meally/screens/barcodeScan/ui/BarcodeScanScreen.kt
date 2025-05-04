package com.meally.meally.screens.barcodeScan.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.meally.domain.barcode.Barcode
import com.meally.meally.common.barcodeAnalyzer.BarcodeAnalyzer
import com.meally.meally.common.barcodeAnalyzer.BarcodeScanView
import com.meally.meally.common.components.BasicText
import com.meally.meally.common.components.VerticalSpacer
import com.meally.meally.common.theme.Typography
import com.meally.meally.screens.barcodeScan.ui.model.BarcodeScanViewState
import com.meally.meally.screens.barcodeScan.viewModel.BarcodeScanViewModel
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.compose.viewmodel.koinViewModel

@Destination
@Composable
fun BarcodeScanScreen(barcodeScanViewModel: BarcodeScanViewModel = koinViewModel()) {
    val state = barcodeScanViewModel.viewState.collectAsStateWithLifecycle().value

    val barcodeAnalyzer =
        remember {
            BarcodeAnalyzer(
                process = { Barcode(it ?: "") },
                onSuccess = barcodeScanViewModel::onBarcodeChanged,
                onFailure = {},
            )
        }
    BarcodeScanScreenStateless(
        state = state,
        barcodeScannerView = {
            BarcodeScanView(
                barcodeAnalyzer = barcodeAnalyzer,
                modifier = Modifier,
            )
        },
    )
}

@Composable
fun BarcodeScanScreenStateless(
    state: BarcodeScanViewState,
    barcodeScannerView: @Composable () -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9),
        ) {
            if (!state.isLoading) {
                barcodeScannerView()
            } else {
                CircularProgressIndicator()
            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 24.dp),
        ) {
            VerticalSpacer(24.dp)
            BasicText(
                text = "Barcode detected:",
                style = Typography.body1,
            )

            if (state.isLoading) {
                VerticalSpacer(24.dp)
                BasicText(
                    text = state.barcode,
                    style = Typography.numbers1,
                )
            }
        }
    }
}

@Preview
@Composable
private fun BarcodeScanScreenPreview() {
    BarcodeScanScreenStateless(
        state =
            BarcodeScanViewState(
                isLoading = false,
                barcode = "123456789",
            ),
    )
}
