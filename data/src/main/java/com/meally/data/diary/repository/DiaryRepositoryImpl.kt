package com.meally.data.diary.repository

import com.meally.data.diary.dto.FoodEntryInsertDto
import com.meally.data.diary.dto.toDomain
import com.meally.data.remote.network.api.MeallyAppApi
import com.meally.data.util.safeApiCall
import com.meally.domain.common.util.map
import com.meally.domain.diary.DiaryRepository
import com.meally.domain.food.Food
import com.meally.domain.mealType.MealType
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DiaryRepositoryImpl(
    private val meallyAppApi: MeallyAppApi,
): DiaryRepository {

    override suspend fun getDiaryEntry(date: LocalDate) = safeApiCall {
        meallyAppApi.diary(date)
    }.map {
        it.map {
          it.toDomain()
        }
    }

    override suspend fun enterFood(date: LocalDate, food: Food, mealType: MealType, amount: Double) = safeApiCall<Unit> {
        meallyAppApi.foodEntry(
            FoodEntryInsertDto(
                foodId = food.id,
                mealType = mealType.name,
                amount = amount,
                date = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            )
        )
    }

}