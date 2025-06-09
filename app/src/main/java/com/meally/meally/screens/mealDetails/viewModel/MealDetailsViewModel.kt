package com.meally.meally.screens.mealDetails.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meally.domain.common.util.onSuccess
import com.meally.domain.meal.Meal
import com.meally.domain.meal.MealRepository
import com.meally.meally.common.navigation.Navigator
import com.meally.meally.screens.destinations.MealEntryScreenDestination
import com.meally.meally.screens.mealEntry.ui.MealEntryScreenNavArgs
import com.meally.meally.screens.navArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MealDetailsViewModel(
    private val mealRepository: MealRepository,
    private val navigator: Navigator,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val navArgs = savedStateHandle.navArgs<MealEntryScreenNavArgs>()

    private val meal = MutableStateFlow<Meal?>(null)

    val viewState = meal
        .onStart { loadData() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null,
        )

    private fun loadData() {
        viewModelScope.launch(Dispatchers.Default) {
            mealRepository.loadMeal(navArgs.mealId).onSuccess { loadedMeal ->
                meal.update { loadedMeal }
            }
        }
    }

    fun addToDiary() {
        navigator.navigate(MealEntryScreenDestination(navArgs.mealId))
    }

    fun goBack() {
        navigator.goBack()
    }
}