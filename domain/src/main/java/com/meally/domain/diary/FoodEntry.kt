package com.meally.domain.diary

import com.meally.domain.food.Food
import com.meally.domain.mealType.MealType
import java.time.LocalDate

data class FoodEntry(
    val id: String,
    val food: Food?,
    val mealType: MealType,
    val amount: Double,
    val date: LocalDate,
) {
    companion object {
        val Empty = FoodEntry(
            id = "",
            food = Food.Empty,
            mealType = MealType.Empty,
            amount = 0.0,
            date = LocalDate.MIN,
        )
    }
}

val FoodEntry.calories: Double
    get() = food?.let { food -> food.calories * amount / 100.0 } ?: amount
