package com.meally.meally.screens.barcodeScan.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meally.domain.barcode.Barcode
import com.meally.meally.common.navigation.Navigator
import com.meally.meally.screens.barcodeScan.ui.model.BarcodeScanViewState
import com.meally.meally.screens.destinations.FoodEntryScreenDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class BarcodeScanViewModel(
    private val navigator: Navigator,
) : ViewModel() {
    private val isLoading = MutableStateFlow(false)

    private val barcodeState = MutableStateFlow("")

    val viewState =
        combine(barcodeState, isLoading) { barcode, isLoading ->
            BarcodeScanViewState(
                isLoading = isLoading,
                barcode = barcode,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = BarcodeScanViewState.Initial,
        )

    fun onBarcodeChanged(barcode: Barcode) {
        barcodeState.update { barcode.payload }
        navigator.navigate(FoodEntryScreenDestination(barcode.payload))
        isLoading.update { true }
    }
}
