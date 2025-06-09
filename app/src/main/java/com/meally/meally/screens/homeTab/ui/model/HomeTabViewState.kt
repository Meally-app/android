package com.meally.meally.screens.homeTab.ui.model

import com.meally.domain.diary.FoodEntry
import com.meally.domain.mealType.MealType
import java.time.LocalDate

data class HomeTabViewState(
    val isLoading: Boolean,
    val goalCalories: Int,
    val consumedCalories: Int,
    val exerciseCalories: Int,
    val items: List<FoodListItem>,
    val selectedDate: LocalDate,
    val weight: Double?,
    val caloriesPieChartValues: CaloriesPieChartValues,
)

data class FoodListItem(
    val name: String,
    val mealType: MealType,
    val calories: String,
)

data class CaloriesPieChartValues(
    val consumed: Float,
    val total: Float,
    val exercise: Float,
    val remaining: Float,
)