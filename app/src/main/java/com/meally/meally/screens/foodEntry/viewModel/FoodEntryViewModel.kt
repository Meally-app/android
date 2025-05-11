package com.meally.meally.screens.foodEntry.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meally.domain.barcode.Barcode
import com.meally.domain.common.util.Resource
import com.meally.domain.common.util.onFailure
import com.meally.domain.common.util.onSuccess
import com.meally.domain.diary.DiaryRepository
import com.meally.domain.food.Food
import com.meally.domain.food.FoodRepository
import com.meally.domain.mealType.MealType
import com.meally.domain.mealType.MealTypeRepository
import com.meally.meally.common.navigation.Navigator
import com.meally.meally.screens.foodEntry.mapper.foodEntryMapper
import com.meally.meally.screens.foodEntry.ui.FoodEntryScreenNavArgs
import com.meally.meally.screens.navArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class FoodEntryViewModel(
    private val foodRepository: FoodRepository,
    private val diaryRepository: DiaryRepository,
    private val mealTypeRepository: MealTypeRepository,
    private val navigator: Navigator,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val barcode = savedStateHandle.navArgs<FoodEntryScreenNavArgs>().barcode

    private val food = MutableStateFlow<Resource<Food>>(Resource.Success(Food.Empty))

    private val mealTypes = MutableStateFlow<List<MealType>>(emptyList())

    private val amount = MutableStateFlow(100)

    private val isLoading = MutableStateFlow(true)

    private val selectedMealType = MutableStateFlow(MealType.Empty)

    val state =
        combine(food, amount, isLoading, mealTypes) { food, amount, isLoading, mealTypes ->
            foodEntryMapper(food, amount, isLoading, mealTypes)
        }.onStart { loadFoodData(barcode) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = foodEntryMapper(food.value, amount.value, isLoading.value, mealTypes.value),
            )

    fun amountChanged(value: String) {
        val amount = value.toIntOrNull() ?: return
        this.amount.update { amount }
    }

    fun confirm() {
        isLoading.update { true }
        val foodVal = when (val value = food.value) {
            is Resource.Failure -> Food.Empty
            is Resource.Success -> value.data
        }
        viewModelScope.launch {
            diaryRepository.enterFood(
                date = LocalDate.now(),
                food = foodVal,
                mealType = selectedMealType.value,
                amount = amount.value.toDouble()
            ).onSuccess {
                navigator.goToHome()
            }.onFailure {
                println("[TEST] fail $it")
            }
        }
    }

    fun goBack() {
        navigator.goToHome()
    }

    fun mealTypeSelected(mealType: MealType) {
        selectedMealType.update { mealType }
    }

    private fun loadFoodData(barcode: String) {
        viewModelScope.launch(Dispatchers.Default) {
            listOf(
                async {
                    foodRepository.getFood(Barcode(barcode)).let { food ->
                        this@FoodEntryViewModel.food.update { food }
                    }
                },
                async {
                    mealTypeRepository.getMealTypes().onSuccess { mealTypes ->
                        this@FoodEntryViewModel.mealTypes.update { mealTypes }
                        this@FoodEntryViewModel.selectedMealType.update { mealTypes.firstOrNull() ?: MealType.Empty }
                    }
                }
            ).awaitAll()
            isLoading.update { false }
        }
    }
}
