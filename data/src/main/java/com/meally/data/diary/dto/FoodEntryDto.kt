package com.meally.data.diary.dto

import com.meally.data.food.dto.FoodDto
import com.meally.data.food.dto.toDomain
import com.meally.data.mealType.dto.MealTypeDto
import com.meally.data.mealType.dto.toDomain
import com.meally.domain.diary.FoodEntry
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Serializable
data class FoodEntryDto(
    val id: String,
    val food: FoodDto?,
    val mealType: MealTypeDto,
    val amount: Double,
    val date: String,
)

fun FoodEntryDto.toDomain() = FoodEntry(
    id = id,
    food = food?.toDomain(),
    mealType = mealType.toDomain(),
    amount = amount,
    date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
)
