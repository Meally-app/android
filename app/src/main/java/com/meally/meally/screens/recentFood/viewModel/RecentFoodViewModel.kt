package com.meally.meally.screens.recentFood.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meally.domain.common.util.onSuccess
import com.meally.domain.food.Food
import com.meally.domain.food.FoodRepository
import com.meally.meally.common.navigation.Navigator
import com.meally.meally.screens.destinations.FoodEntryScreenDestination
import com.meally.meally.screens.recentFood.mapper.recentFoodViewStateMapper
import com.meally.meally.screens.recentFood.ui.model.RecentFoodItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecentFoodViewModel(
    private val navigator: Navigator,
    private val foodRepository: FoodRepository,
): ViewModel() {

    fun goBack() {
        navigator.goBack()
    }

    fun itemClicked(item: RecentFoodItem) {
        navigator.navigate(FoodEntryScreenDestination(barcode = item.barcode))
    }

    private val isLoading = MutableStateFlow(true)

    private val food = MutableStateFlow(listOf<Food>())

    val viewState = combine(food, isLoading) { food, isLoading ->
        recentFoodViewStateMapper(food, isLoading)
    }.onStart {
       loadData()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = recentFoodViewStateMapper(food.value, isLoading.value),
    )

    private fun loadData() {
        isLoading.update { true }
        viewModelScope.launch(Dispatchers.Default) {
            foodRepository.recentFood().onSuccess { recentFood ->
                food.update { recentFood }
            }
            isLoading.update { false }
        }
    }
}