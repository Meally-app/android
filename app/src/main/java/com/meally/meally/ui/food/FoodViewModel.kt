package com.meally.meally.ui.food

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meally.domain.barcode.Barcode
import com.meally.domain.food.FoodRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@OptIn(ExperimentalCoroutinesApi::class)
class FoodViewModel(
    foodRepository: FoodRepository,
    private val foodViewStateMapper: FoodViewStateMapper,
) : ViewModel() {
    private val foodFlow = foodRepository.foodFlow

    private val barcodeState = MutableStateFlow("")

    private val foodState: SharedFlow<FoodItemViewState> =
        foodFlow
            .mapLatest { foodViewStateMapper.mapFoodItem(it) }
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                replay = 1,
            )

    val viewState =
        combine(foodState, barcodeState) { food, barcode ->
            FoodViewState(
                isLoadingInitialData = false,
                food = food,
                barcode = barcode,
            )
        }.shareState(FoodViewState.Initial)

    private fun <T> Flow<T>.shareState(initialValue: T) =
        stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = initialValue,
        )

    fun onBarcodeChanged(barcode: Barcode) {
        barcodeState.update { barcode.payload }
    }
}
