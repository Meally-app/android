package com.meally.data.diary.dto

import kotlinx.serialization.Serializable

@Serializable
data class FoodEntryInsertDto(
    val foodId: String,
    val mealType: String,
    val amount: Double,
    val date: String,
)
