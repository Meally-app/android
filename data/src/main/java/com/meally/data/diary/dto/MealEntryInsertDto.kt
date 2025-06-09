package com.meally.data.diary.dto

import kotlinx.serialization.Serializable

@Serializable
data class MealEntryInsertDto(
    val mealId: String,
    val mealType: String,
    val amount: Double,
    val date: String,
)
