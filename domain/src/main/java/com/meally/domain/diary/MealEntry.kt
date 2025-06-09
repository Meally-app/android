package com.meally.domain.diary

import com.meally.domain.meal.Meal
import com.meally.domain.meal.calories
import com.meally.domain.mealType.MealType
import java.time.LocalDate

data class MealEntry(
    val meal: Meal,
    val mealType: MealType,
    val amount: Double,
    val date: LocalDate,
)

val MealEntry.calories : Double
    get() = meal.calories * amount
