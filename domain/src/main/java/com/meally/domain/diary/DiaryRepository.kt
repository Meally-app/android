package com.meally.domain.diary

import com.meally.domain.common.util.Resource
import com.meally.domain.food.Food
import com.meally.domain.meal.Meal
import com.meally.domain.mealType.MealType
import java.time.LocalDate

interface DiaryRepository {

    suspend fun getDiaryEntry(date: LocalDate): Resource<DiaryEntry>

    suspend fun getDiarySummary(from: LocalDate, to: LocalDate): Resource<List<DiarySummaryDay>>

    suspend fun enterFood(date: LocalDate, food: Food?, mealType: MealType, amount: Double): Resource<Unit>

    suspend fun enterMeal(date: LocalDate, meal: Meal, mealType: MealType, amount: Double): Resource<Unit>
}