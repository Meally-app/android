package com.meally.meally.screens.createMeal.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meally.domain.barcode.Barcode
import com.meally.domain.common.util.onSuccess
import com.meally.domain.food.FoodRepository
import com.meally.domain.meal.FoodInMeal
import com.meally.domain.meal.Meal
import com.meally.domain.meal.MealRepository
import com.meally.domain.meal.MealVisibility
import com.meally.meally.common.navigation.Navigator
import com.meally.meally.screens.createMeal.ui.CreateMealScreenNavArgs
import com.meally.meally.screens.destinations.SearchFoodScreenDestination
import com.meally.meally.screens.destinations.UserMealsScreenDestination
import com.meally.meally.screens.navArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateMealViewModel(
    private val mealRepository: MealRepository,
    private val foodRepository: FoodRepository,
    private val navigator: Navigator,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val navArgs = savedStateHandle.navArgs<CreateMealScreenNavArgs>()

    private val meal = MutableStateFlow(Meal.Empty.copy(visibility = MealVisibility.PRIVATE))

    val viewState = meal
        .onStart { loadData() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = meal.value,
        )


    fun addFood(barcode: String) {
        viewModelScope.launch(Dispatchers.Default) {
            val food = foodRepository.getFood(Barcode(barcode))
            food.onSuccess { newFood ->
                if (meal.value.foodInMeal.firstOrNull { it.food.id == newFood.id } != null) return@onSuccess

                val foodList = mutableListOf<FoodInMeal>()
                foodList.addAll(meal.value.foodInMeal)

                foodList.add(FoodInMeal(food = newFood, amount = 100.0))
                meal.update {
                    it.copy(
                        foodInMeal = foodList
                    )
                }
            }
        }
    }

    fun searchFood() {
        navigator.navigate(SearchFoodScreenDestination(goBackToMealCreation = true))
    }

    fun goBack() {
        navigator.goBack()
    }

    fun mealChanged(newMeal: Meal) {
        meal.update { newMeal }
    }

    fun confirm() {
        viewModelScope.launch(Dispatchers.Default) {
            if (meal.value.id.isEmpty()) {
                mealRepository.createMeal(
                    meal.value.name,
                    meal.value.visibility,
                    meal.value.foodInMeal,
                )
            } else {
                mealRepository.editMeal(meal.value)
            }
            navigator.goToHome()
        }
    }

    private fun loadData() {
        viewModelScope.launch(Dispatchers.Default) {
            if (navArgs.mealId != null) {
                mealRepository.loadMeal(navArgs.mealId).onSuccess { loadedMeal ->
                    meal.update { loadedMeal }
                }
            }
        }
    }

}