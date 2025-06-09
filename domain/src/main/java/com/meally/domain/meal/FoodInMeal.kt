package com.meally.domain.meal

import com.meally.domain.food.Food

data class FoodInMeal(
    val food: Food,
    val amount: Double,
)
