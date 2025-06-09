package com.meally.data.diary.dto

import com.meally.domain.diary.DiaryEntry
import kotlinx.serialization.Serializable

@Serializable
data class DiaryForDateResponseDto(
    val food: List<FoodEntryDto>,
    val exercise: List<ExerciseEntryDto>,
    val meals: List<MealEntryDto>,
    val goalCalories: Int,
)

fun DiaryForDateResponseDto.toDomain() = DiaryEntry(
    food = food.map { it.toDomain() },
    exercise = exercise.map { it.toDomain() },
    meals = meals.map { it.toDomain() },
    goalCalories = goalCalories,
)
