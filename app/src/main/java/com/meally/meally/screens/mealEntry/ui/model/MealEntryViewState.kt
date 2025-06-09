package com.meally.meally.screens.mealEntry.ui.model

import com.meally.domain.meal.Meal
import com.meally.domain.meal.calories
import com.meally.domain.mealType.MealType
import java.text.NumberFormat
import java.time.LocalDate

data class MealEntryViewState(
    val meal: MealViewState,
    val mealTypeOptions: Map<String, MealType>,
    val selectedDate: LocalDate,
    val isLoading: Boolean,
)

data class MealViewState(
    val name: String,
    val calories: String,
    val carbs: String,
    val protein: String,
    val fat: String,
)

fun Meal.toViewState(amount: Double): MealViewState {
    val calories = formatter.format(amount * calories)
    val carbs = formatter.format(amount * foodInMeal.sumOf { it.food.carbs * it.amount / 100 })
    val protein = formatter.format(amount * foodInMeal.sumOf { it.food.protein * it.amount / 100 })
    val fat = formatter.format(amount * foodInMeal.sumOf { it.food.fat * it.amount / 100 })

    return MealViewState(
        name = name,
        calories = calories,
        carbs = carbs,
        protein = protein,
        fat = fat,
    )
}

private val formatter =
    NumberFormat.getInstance().apply {
        minimumFractionDigits = 1
        maximumFractionDigits = 1
    }