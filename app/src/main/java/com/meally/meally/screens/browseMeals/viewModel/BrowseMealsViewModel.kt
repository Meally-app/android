package com.meally.meally.screens.browseMeals.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meally.domain.common.util.onSuccess
import com.meally.domain.meal.BrowseMeal
import com.meally.domain.meal.MealRepository
import com.meally.meally.common.navigation.Navigator
import com.meally.meally.screens.browseMeals.mapper.browseMealsMapper
import com.meally.meally.screens.browseMeals.state.BrowseMealsSearchState
import com.meally.meally.screens.destinations.MealDetailsScreenDestination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BrowseMealsViewModel(
    private val mealRepository: MealRepository,
    private val navigator: Navigator,
) : ViewModel() {

    private val meals = MutableStateFlow<List<BrowseMeal>>(emptyList())

    private val isLoading = MutableStateFlow(false)

    private val searchTerm = MutableStateFlow("")
    private val caloriesMin = MutableStateFlow(0.0)
    private val caloriesMax = MutableStateFlow(5000.0)

    private val searchState = combine(searchTerm, caloriesMin, caloriesMax) { searchTerm, caloriesMin, caloriesMax ->
        BrowseMealsSearchState(
            searchTerm = searchTerm,
            caloriesMin = caloriesMin,
            caloriesMax = caloriesMax,
        )
    }.onEach {
        loadMeals(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = BrowseMealsSearchState(searchTerm.value, caloriesMin.value, caloriesMax.value)
    )

    val viewState = combine(isLoading, meals, searchState, ::browseMealsMapper)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = browseMealsMapper(isLoading.value, meals.value, searchState.value),
        )

    fun searchTermChanged(newTerm: String) {
        searchTerm.update { newTerm }
    }

    fun caloriesMinChanged(newMin: Double) {
        caloriesMin.update { newMin }
    }

    fun caloriesMaxChanged(newMax: Double) {
        caloriesMax.update { newMax }
    }

    fun mealClicked(meal: BrowseMeal) {
        navigator.navigate(MealDetailsScreenDestination(meal.id))
    }

    private fun loadMeals(state: BrowseMealsSearchState) {
        isLoading.update { true }
        viewModelScope.launch(Dispatchers.Default) {
            with(state) {
                mealRepository.browseMeals(
                    searchQuery = searchTerm,
                    caloriesMin = caloriesMin,
                    caloriesMax = caloriesMax,
                )
            }.onSuccess { newMeals ->
                meals.update { newMeals }
            }
            isLoading.update { false }
        }
    }
}