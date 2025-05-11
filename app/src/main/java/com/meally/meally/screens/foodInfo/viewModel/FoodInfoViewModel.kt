package com.meally.meally.screens.foodInfo.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meally.domain.barcode.Barcode
import com.meally.domain.common.util.Resource
import com.meally.domain.food.FoodRepository
import com.meally.meally.common.navigation.Navigator
import com.meally.meally.screens.foodInfo.ui.FoodInfoScreenNavArgs
import com.meally.meally.common.food.viewState.FoodInfoViewState
import com.meally.meally.common.food.viewState.toViewState
import com.meally.meally.screens.navArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FoodInfoViewModel(
    private val foodRepository: FoodRepository,
    private val navigator: Navigator,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val barcode = savedStateHandle.navArgs<FoodInfoScreenNavArgs>().barcode

    private val _state = MutableStateFlow<FoodInfoViewState>(FoodInfoViewState.Loading)
    val state =
        _state
            .onStart { loadFoodData(barcode) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = _state.value,
            )

    private fun loadFoodData(barcode: String) {
        viewModelScope.launch(Dispatchers.Default) {
            foodRepository.getFood(Barcode(barcode)).let { food ->
                _state.update {
                    when (food) {
                        is Resource.Failure -> FoodInfoViewState.Error
                        is Resource.Success ->
                            FoodInfoViewState.Loaded(
                                foodItem = food.data.toViewState(),
                            )
                    }
                }
            }
        }
    }

    fun goBack() {
        navigator.goToHome()
    }
}
