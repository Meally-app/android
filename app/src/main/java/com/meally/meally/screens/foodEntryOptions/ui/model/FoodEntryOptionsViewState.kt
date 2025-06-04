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
    RECENT("Recently entered", R.drawable.ic_recent),
    SEARCH("Search food", R.drawable.ic_search),
    MANUAL("Manual entry", R.drawable.ic_edit),
}
