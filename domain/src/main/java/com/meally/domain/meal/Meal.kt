package com.meally.domain.meal

import com.meally.domain.user.User

data class Meal(
    val id: String,
    val name: String,
    val user: User,
    val visibility: MealVisibility,
    val foodInMeal: List<FoodInMeal>,
    val isLiked: Boolean,
) {
    companion object {
        val Empty = Meal(
            id = "",
            name = "",
            user = User.Empty,
            visibility = MealVisibility.UNKNOWN,
            foodInMeal = emptyList(),
            isLiked = false,
        )
    }
}

val Meal.calories: Double
    get() = foodInMeal.sumOf { it.amount * it.food.calories / 100 }
