package com.meally.meally.screens.homeTab

import com.meally.domain.diary.DiaryEntry
import com.meally.domain.diary.ExerciseEntry
import com.meally.domain.diary.FoodEntry
import com.meally.domain.diary.MealEntry
import com.meally.domain.diary.calories
import com.meally.domain.weight.Weight
import com.meally.meally.screens.homeTab.ui.model.CaloriesPieChartValues
import com.meally.meally.screens.homeTab.ui.model.DiaryEntryItem
import com.meally.meally.screens.homeTab.ui.model.FoodListItem
import com.meally.meally.screens.homeTab.ui.model.HomeTabViewState
import java.time.LocalDate

fun homeTabMapper(diaryEntry: DiaryEntry?, selectedDate: LocalDate, isLoading: Boolean, weight: Weight?) = HomeTabViewState(
    isLoading = isLoading,
    items = mapItems(diaryEntry),
    goalCalories = diaryEntry?.goalCalories ?: 2000,
    consumedCalories = diaryEntry?.let { mapConsumedCalories(it.food, it.meals).toInt() } ?: 0,
    exerciseCalories = diaryEntry?.let { mapExerciseCalories(it.exercise) } ?: 0,
    selectedDate = selectedDate,
    weight = weight?.weight,
    caloriesPieChartValues = mapDonutChart(diaryEntry)
)

private fun mapItems(diaryEntry: DiaryEntry?): List<FoodListItem> {
    if (diaryEntry == null) return listOf()

    return buildList {
        diaryEntry.food.forEach {
            add(
                FoodListItem(
                    name = it.food?.name ?: "Manual entry",
                    mealType = it.mealType,
                    calories = it.calories.toInt().toString(),
                    item = DiaryEntryItem.Food(it),
                )
            )
        }
        diaryEntry.meals.forEach {
            add(
                FoodListItem(
                    name = it.meal.name,
                    mealType = it.mealType,
                    calories = it.calories.toInt().toString(),
                    item = DiaryEntryItem.Meal(it),
                )
            )
        }
    }.sortedBy { it.mealType.orderInDay }
}

private fun mapConsumedCalories(food: List<FoodEntry>, meals: List<MealEntry>) = food.sumOf { it.calories } + meals.sumOf { it.calories }

private fun mapExerciseCalories(exercise: List<ExerciseEntry>) = exercise.sumOf { it.calories }.toInt()

private fun mapDonutChart(diaryEntry: DiaryEntry?): CaloriesPieChartValues {
    if (diaryEntry == null) {
        return CaloriesPieChartValues(
            consumed = 0f,
            remaining = 2000f,
            total = 2000f,
            exercise = 0f,
            remainingText = "2000\nLeft"
        )
    }
    val consumed = mapConsumedCalories(diaryEntry.food, diaryEntry.meals)
    val exercise = mapExerciseCalories(diaryEntry.exercise)
    val total = diaryEntry.goalCalories + exercise
    val remaining = total - consumed
    val remainingRounded = remaining.toInt()
    return CaloriesPieChartValues(
        consumed = consumed.toFloat(),
        exercise = exercise.toFloat(),
        total = total.toFloat(),
        remaining = remaining.toFloat().takeIf { it > 0 } ?: 0f,
        remainingText = if (remaining > 0) "$remainingRounded\nLeft" else "${-remainingRounded}\nOver"
    )
}