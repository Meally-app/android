package com.meally.meally.screens.homeTab

import com.meally.domain.diary.DiaryEntry
import com.meally.domain.diary.ExerciseEntry
import com.meally.domain.diary.FoodEntry
import com.meally.domain.weight.Weight
import com.meally.meally.screens.homeTab.ui.model.CaloriesPieChartValues
import com.meally.meally.screens.homeTab.ui.model.HomeTabViewState
import java.time.LocalDate

fun homeTabMapper(diaryEntry: DiaryEntry?, selectedDate: LocalDate, isLoading: Boolean, weight: Weight?) = HomeTabViewState(
    isLoading = isLoading,
    food = diaryEntry?.food ?: listOf(),
    goalCalories = diaryEntry?.goalCalories ?: 2000,
    consumedCalories = diaryEntry?.let { mapConsumedCalories(it.food) } ?: 0,
    exerciseCalories = diaryEntry?.let { mapExerciseCalories(it.exercise) } ?: 0,
    selectedDate = selectedDate,
    weight = weight?.weight,
    caloriesPieChartValues = mapDonutChart(diaryEntry)
)

private fun mapConsumedCalories(food: List<FoodEntry>) = food.sumOf { it.food?.let { food -> food.calories * it.amount / 100.0 } ?: it.amount  }.toInt()

private fun mapExerciseCalories(exercise: List<ExerciseEntry>) = exercise.sumOf { it.calories }.toInt()

private fun mapDonutChart(diaryEntry: DiaryEntry?): CaloriesPieChartValues {
    if (diaryEntry == null) {
        return CaloriesPieChartValues(
            consumed = 0f,
            remaining = 2000f,
            total = 2000f,
            exercise = 0f,
        )
    }
    val consumed = mapConsumedCalories(diaryEntry.food)
    val exercise = mapExerciseCalories(diaryEntry.exercise)
    val total = diaryEntry.goalCalories + exercise
    val remaining = total - consumed
    return CaloriesPieChartValues(
        consumed = consumed.toFloat(),
        exercise = exercise.toFloat(),
        total = total.toFloat(),
        remaining = remaining.toFloat(),
    )
}