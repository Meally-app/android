package com.meally.data.meals.dto

import com.meally.data.user.dto.UserDto
import com.meally.data.user.dto.toDomain
import com.meally.domain.meal.Meal
import com.meally.domain.meal.MealVisibility
import kotlinx.serialization.Serializable

@Serializable
data class MealDto(
    val id: String,
    val name: String,
    val user: UserDto,
    val status: String,
    val foodInMeal: List<FoodInMealDto>,
    val isLiked: Boolean = false,
)

fun MealDto.toDomain() = Meal(
    id = id,
    name = name,
    user = user.toDomain(),
    visibility = MealVisibility.safeValueOf(status),
    foodInMeal = foodInMeal.map { it.toDomain() },
    isLiked = isLiked,
)
