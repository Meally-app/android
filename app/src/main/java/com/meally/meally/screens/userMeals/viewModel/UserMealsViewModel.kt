package com.meally.meally.screens.userMeals.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meally.domain.common.util.onSuccess
import com.meally.domain.meal.Meal
import com.meally.domain.meal.MealRepository
import com.meally.meally.common.navigation.Navigator
import com.meally.meally.screens.destinations.CreateMealScreenDestination
import com.meally.meally.screens.destinations.MealDetailsScreenDestination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserMealsViewModel(
    private val mealRepository: MealRepository,
    private val navigator: Navigator,
) : ViewModel() {

    private val meals = MutableStateFlow<List<Meal>>(listOf())

    private val isLoading = MutableStateFlow(true)

    val viewState = meals.onStart {
        loadData()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = listOf()
    )

    fun goBack() {
        navigator.goBack()
    }

    fun mealClicked(meal: Meal) {
        navigator.navigate(MealDetailsScreenDestination(meal.id))
    }

    fun createMeal() {
        navigator.navigate(CreateMealScreenDestination())
    }

    private fun loadData() {
        viewModelScope.launch(Dispatchers.Default) {
            mealRepository.myMeals().onSuccess { userMeals ->
                meals.update { userMeals }
            }
            isLoading.update { false }
        }
    }
}