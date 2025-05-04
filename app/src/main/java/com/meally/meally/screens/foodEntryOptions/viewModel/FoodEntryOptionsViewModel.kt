package com.meally.meally.screens.foodEntryOptions.viewModel

import androidx.lifecycle.ViewModel
import com.meally.meally.common.navigation.Navigator
import com.meally.meally.screens.destinations.BarcodeScanScreenDestination
import com.meally.meally.screens.foodEntryOptions.ui.model.FoodEntryOption

class FoodEntryOptionsViewModel(
    private val navigator: Navigator,
) : ViewModel() {
    fun optionClicked(option: FoodEntryOption) {
        when (option) {
            FoodEntryOption.BARCODE -> navigator.navigate(BarcodeScanScreenDestination)
            FoodEntryOption.MANUAL -> Unit // todo matko navigate
        }
    }

    fun goBack() {
        navigator.goBack()
    }
}
