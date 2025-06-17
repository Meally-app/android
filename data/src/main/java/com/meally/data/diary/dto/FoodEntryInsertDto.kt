package com.meally.data.diary.dto

import kotlinx.serialization.Serializable

@Serializable
data class FoodEntryInsertDto(
    val foodEntryId: String?,
    val foodId: String?,
    val mealType: String,
    val amount: Double,
    val date: String,
)
