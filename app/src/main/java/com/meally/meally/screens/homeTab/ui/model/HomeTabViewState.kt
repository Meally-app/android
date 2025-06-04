package com.meally.meally.screens.homeTab.ui.model

import com.meally.domain.diary.FoodEntry
import java.time.LocalDate

data class HomeTabViewState(
    val isLoading: Boolean,
    val goalCalories: Int,
    val consumedCalories: Int,
    val exerciseCalories: Int,
    val food: List<FoodEntry>,
    val selectedDate: LocalDate,
    val weight: Double?,
    val caloriesPieChartValues: CaloriesPieChartValues,
)

data class CaloriesPieChartValues(
    val consumed: Float,
    val total: Float,
    val exercise: Float,
    val remaining: Float,
)