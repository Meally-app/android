package com.meally.data.meals.dto

import com.meally.domain.meal.FoodInMeal
import kotlinx.serialization.Serializable

@Serializable
data class InsertMealDto(
    val mealId: String? = null,
    val name: String,
    val visibility: String,
    val foodInMeal: List<InsertFoodInMealDto>,
)

@Serializable
data class InsertFoodInMealDto(
    val foodId: String,
    val amount: Double,
)

fun FoodInMeal.toDto() = InsertFoodInMealDto(
    foodId = food.id,
    amount = amount,
)

