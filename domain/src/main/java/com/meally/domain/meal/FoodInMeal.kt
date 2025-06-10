package com.meally.domain.meal

import com.meally.domain.food.Food

data class FoodInMeal(
    val food: Food,
    val amount: Double,
)

val FoodInMeal.calories: Double
    get() = amount * food.calories / 100
