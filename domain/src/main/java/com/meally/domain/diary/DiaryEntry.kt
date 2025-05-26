package com.meally.domain.diary

import com.meally.domain.food.Food
import com.meally.domain.mealType.MealType
import java.time.LocalDate

data class DiaryEntry(
    val food: Food?,
    val mealType: MealType,
    val amount: Double,
    val date: LocalDate,
) {
    companion object {
        val Empty = DiaryEntry(
            food = Food.Empty,
            mealType = MealType.Empty,
            amount = 0.0,
            date = LocalDate.MIN,
        )
    }
}
