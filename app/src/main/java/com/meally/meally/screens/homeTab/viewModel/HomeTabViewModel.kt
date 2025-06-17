package com.meally.meally.screens.homeTab.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meally.domain.common.util.onSuccess
import com.meally.domain.diary.DiaryEntry
import com.meally.domain.diary.FoodEntry
import com.meally.domain.diary.DiaryRepository
import com.meally.domain.diary.MealEntry
import com.meally.domain.weight.Weight
import com.meally.domain.weight.WeightRepository
import com.meally.meally.common.navigation.Navigator
import com.meally.meally.screens.destinations.FoodEntryScreenDestination
import com.meally.meally.screens.destinations.MealEntryScreenDestination
import com.meally.meally.screens.destinations.WeightEntryScreenDestination
import com.meally.meally.screens.homeTab.homeTabMapper
import com.meally.meally.screens.homeTab.ui.model.DiaryEntryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class HomeTabViewModel(
    private val diaryRepository: DiaryRepository,
    private val weightRepository: WeightRepository,
    private val navigator: Navigator,
): ViewModel() {

    private val selectedDate = MutableStateFlow(LocalDate.now())

    private val diaryEntry = MutableStateFlow<DiaryEntry?>(null)

    private val weight = MutableStateFlow<Weight?>(null)

    private val isLoading = MutableStateFlow(true)

    val state = combine(diaryEntry, selectedDate, isLoading, weight) { diaryEntry, selectedDate, isLoading, weight ->
        homeTabMapper(diaryEntry, selectedDate, isLoading, weight)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = homeTabMapper(diaryEntry.value, selectedDate.value, isLoading.value, weight.value)
    )

    fun entryClicked(item: DiaryEntryItem) {
        when (item) {
            is DiaryEntryItem.Food -> navigator.navigate(
                FoodEntryScreenDestination(
                    barcode = item.entry.food?.barcode,
                    amount = item.entry.amount.toInt(),
                    mealType = item.entry.mealType.name,
                    date = item.entry.date,
                    foodEntryId = item.entry.id,
                )
            )
            is DiaryEntryItem.Meal -> navigator.navigate(
                MealEntryScreenDestination(
                    mealId = item.entry.meal.id,
                )
            )
        }
    }

    fun refresh() {
        viewModelScope.launch(Dispatchers.Default) {
            listOf(
                async { diaryRepository.getDiaryEntry(selectedDate.value).onSuccess { diary -> diaryEntry.update { diary } } },
                async { weightRepository.getWeightForDate(selectedDate.value).onSuccess { todayWeight -> weight.update { todayWeight } } },
            ).awaitAll()
            isLoading.update { false }
        }
    }

    fun selectDate(date: LocalDate) {
        isLoading.update { true }
        selectedDate.update { date }
        refresh()
    }

    fun addWeight() {
        navigator.navigate(WeightEntryScreenDestination(selectedDate.value))
    }
}