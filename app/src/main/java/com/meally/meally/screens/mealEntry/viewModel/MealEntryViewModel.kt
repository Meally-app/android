package com.meally.meally.screens.mealEntry.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meally.domain.common.util.onFailure
import com.meally.domain.common.util.onSuccess
import com.meally.domain.diary.DiaryRepository
import com.meally.domain.meal.Meal
import com.meally.domain.meal.MealRepository
import com.meally.domain.mealType.MealType
import com.meally.domain.mealType.MealTypeRepository
import com.meally.meally.common.navigation.Navigator
import com.meally.meally.screens.mealDetails.ui.MealDetailsScreenNavArgs
import com.meally.meally.screens.mealEntry.ui.MealEntryScreenNavArgs
import com.meally.meally.screens.mealEntry.ui.model.MealEntryViewState
import com.meally.meally.screens.mealEntry.ui.model.toViewState
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

class MealEntryViewModel(
    private val mealRepository: MealRepository,
    private val diaryRepository: DiaryRepository,
    private val navigator: Navigator,
    private val mealTypeRepository: MealTypeRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val navArgs = savedStateHandle.navArgs<MealDetailsScreenNavArgs>()

    private val meal = MutableStateFlow(Meal.Empty)

    private val mealTypes = MutableStateFlow<List<MealType>>(emptyList())

    private val amount = MutableStateFlow(1.0)

    private val isLoading = MutableStateFlow(true)

    private val selectedMealType = MutableStateFlow(MealType.Empty)

    private val selectedDate = MutableStateFlow(LocalDate.now())

    val state =
        combine(meal, amount, isLoading, mealTypes, selectedDate) { meal, amount, isLoading, mealTypes, selectedDate ->
            MealEntryViewState(
                meal = meal.toViewState(amount),
                mealTypeOptions = mealTypes.associateBy { it.name.replaceFirstChar { it.uppercase() } },
                isLoading = isLoading,
                selectedDate = selectedDate,
            )
        }.onStart { loadData() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = MealEntryViewState(Meal.Empty.toViewState(1.0), mapOf(), LocalDate.now(), true),
            )

    fun goBack() {
        navigator.goBack()
    }

    fun amountChanged(value: String) {
        val amount = value.toDoubleOrNull() ?: return
        this.amount.update { amount }
    }

    fun confirm() {
        isLoading.update { true }
        viewModelScope.launch {
            diaryRepository.enterMeal(
                date = selectedDate.value,
                meal = meal.value,
                mealType = selectedMealType.value,
                amount = amount.value
            ).onSuccess {
                navigator.goToHome()
            }.onFailure {
                println("[TEST] failed loading diary $it")
            }
        }
    }

    fun mealTypeSelected(mealType: MealType) {
        selectedMealType.update { mealType }
    }

    fun dateSelected(date: LocalDate) {
        selectedDate.update { date }
    }

    private fun loadData() {
        viewModelScope.launch(Dispatchers.Default) {
            listOf(
                async {
                    mealRepository.loadMeal(navArgs.mealId).onSuccess { loadedMeal ->
                        meal.update { loadedMeal }
                    }
                },
                async {
                    mealTypeRepository.getMealTypes().onSuccess { mealTypes ->
                        this@MealEntryViewModel.mealTypes.update { mealTypes }
                        this@MealEntryViewModel.selectedMealType.update { mealTypes.firstOrNull() ?: MealType.Empty }
                    }
                }
            ).awaitAll()
            isLoading.update { false }
        }
    }
}