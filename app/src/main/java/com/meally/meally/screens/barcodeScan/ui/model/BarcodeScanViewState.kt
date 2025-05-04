package com.meally.meally.screens.barcodeScan.ui.model

data class BarcodeScanViewState(
    val isLoading: Boolean,
    val barcode: String,
) {
    companion object {
        val Initial =
            BarcodeScanViewState(
                isLoading = false,
                barcode = "",
            )
    }
}
