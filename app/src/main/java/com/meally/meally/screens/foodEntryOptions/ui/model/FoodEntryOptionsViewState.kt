package com.meally.meally.screens.foodEntryOptions.ui.model

import androidx.annotation.DrawableRes
import com.meally.meally.R

data class FoodEntryOptionsViewState(
    val options: List<FoodEntryOption>,
)

enum class FoodEntryOption(
    val text: String,
    @DrawableRes val icon: Int,
) {
    BARCODE("Scan barcode", R.drawable.ic_scan_barcode),
    MANUAL("Manual entry", R.drawable.ic_edit),
}
