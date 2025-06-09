package com.meally.data.diary.repository

import com.meally.data.diary.dto.FoodEntryInsertDto
import com.meally.data.diary.dto.MealEntryInsertDto
import com.meally.data.diary.dto.toDomain
import com.meally.data.meals.dto.toDomain
import com.meally.data.remote.network.api.MeallyAppApi
import com.meally.data.util.safeApiCall
import com.meally.domain.common.util.Resource
import com.meally.domain.common.util.map
import com.meally.domain.diary.DiaryRepository
import com.meally.domain.diary.DiarySummaryDay
import com.meally.domain.food.Food
import com.meally.domain.meal.Meal
import com.meally.domain.mealType.MealType
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DiaryRepositoryImpl(
    private val meallyAppApi: MeallyAppApi,
): DiaryRepository {

    override suspend fun getDiaryEntry(date: LocalDate) = safeApiCall {
        meallyAppApi.diary(date)
    }.map {
        it.toDomain()
    }

    override suspend fun getDiarySummary(from: LocalDate, to: LocalDate): Resource<List<DiarySummaryDay>> = safeApiCall {
        meallyAppApi.diarySummary(from, to)
    }.map {
        it.map {
            it.toDomain()
        }
    }

    override suspend fun enterFood(date: LocalDate, food: Food?, mealType: MealType, amount: Double) = safeApiCall<Unit> {
        meallyAppApi.foodEntry(
            FoodEntryInsertDto(
                foodId = food?.id,
                mealType = mealType.name,
                amount = amount,
                date = date.format(dateFormatter)
            )
        )
    }

    override suspend fun enterMeal(date: LocalDate, meal: Meal, mealType: MealType, amount: Double) = safeApiCall<Unit> {
        meallyAppApi.mealEntry(
            MealEntryInsertDto(
                mealId = meal.id,
                mealType = mealType.name,
                amount = amount,
                date = date.format(dateFormatter),
            )
        )
    }

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
}