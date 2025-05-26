package com.meally.meally.screens.foodEntryOptions.mapper

import com.meally.meally.screens.foodEntryOptions.ui.model.FoodEntryOption
import com.meally.meally.screens.foodEntryOptions.ui.model.FoodEntryOptionsViewState

fun foodEntryOptionsMapper() =
    FoodEntryOptionsViewState(
        options = listOf(FoodEntryOption.BARCODE, FoodEntryOption.SEARCH, FoodEntryOption.MANUAL),
    )
