package com.meally.data.diary.dto

import com.meally.data.mealType.dto.MealTypeDto
import com.meally.data.mealType.dto.toDomain
import com.meally.data.meals.dto.MealDto
import com.meally.data.meals.dto.toDomain
import com.meally.domain.diary.MealEntry
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Serializable
data class MealEntryDto(
    val meal: MealDto,
    val mealType: MealTypeDto,
    val amount: Double,
    val date: String,
)

fun MealEntryDto.toDomain() = MealEntry(
    meal = meal.toDomain(),
    mealType = mealType.toDomain(),
    amount = amount,
    date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
)
