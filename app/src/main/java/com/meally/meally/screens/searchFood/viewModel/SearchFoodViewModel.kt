package com.meally.meally.screens.searchFood.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meally.domain.common.util.onSuccess
import com.meally.domain.food.Food
import com.meally.domain.food.FoodRepository
import com.meally.meally.common.navigation.Navigator
import com.meally.meally.screens.destinations.FoodEntryScreenDestination
import com.meally.meally.screens.destinations.FoodEntryScreenDestination.invoke
import com.meally.meally.screens.searchFood.mapper.searchFoodViewStateMapper
import com.meally.meally.screens.searchFood.ui.model.SearchFoodItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchFoodViewModel(
    private val foodRepository: FoodRepository,
    private val navigator: Navigator,
) : ViewModel() {

    private val userInput = MutableStateFlow("")

    private val food = MutableStateFlow(listOf<Food>())

    private val isLoading = MutableStateFlow(false)

    val viewState = combine(food, isLoading) { food, isLoading ->
        searchFoodViewStateMapper(food, isLoading)
    }.onStart {
        viewModelScope.launch(Dispatchers.Default){
            userInput
                .debounce(300)
                .distinctUntilChanged()
                .collectLatest {
                    onSearchChanged(it)
                }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = searchFoodViewStateMapper(food.value, isLoading.value),
    )

    fun updateUserInput(query: String) {
        userInput.update { query }
    }

    fun goBack() {
        navigator.goBack()
    }

    fun itemClicked(item: SearchFoodItem) {
        navigator.navigate(FoodEntryScreenDestination(item.barcode))
    }

    private fun onSearchChanged(query: String) {
        if (query.isEmpty()) {
            food.update { listOf() }
            return
        }
        isLoading.update { true }
        viewModelScope.launch(Dispatchers.Default) {
            foodRepository.search(query).onSuccess { searchedFood ->
                food.update { searchedFood }
            }
            isLoading.update { false }
        }
    }
}