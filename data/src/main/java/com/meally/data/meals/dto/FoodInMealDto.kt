package com.meally.data.meals.dto

import com.meally.data.food.dto.FoodDto
import com.meally.data.food.dto.toDomain
import com.meally.domain.meal.FoodInMeal
import kotlinx.serialization.Serializable

@Serializable
data class FoodInMealDto(
    val food: FoodDto,
    val amount: Double,
)

fun FoodInMealDto.toDomain() = FoodInMeal(
    food = food.toDomain(),
    amount = amount,
)
