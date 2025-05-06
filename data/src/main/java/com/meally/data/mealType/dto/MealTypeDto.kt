package com.meally.data.mealType.dto

import com.meally.domain.mealType.MealType
import kotlinx.serialization.Serializable

@Serializable
data class MealTypeDto(
    val name: String,
    val orderInDay: Int,
)

fun MealTypeDto.toDomain() = MealType(
    name = name,
    orderInDay = orderInDay,
)
