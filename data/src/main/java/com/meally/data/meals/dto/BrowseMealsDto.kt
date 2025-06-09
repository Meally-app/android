package com.meally.data.meals.dto

import com.meally.data.user.dto.UserDto
import com.meally.data.user.dto.toDomain
import com.meally.domain.meal.BrowseMeal

data class BrowseMealsDto(
    val id: String,
    val name: String,
    val user: UserDto,
    val calories: Double,
)

fun BrowseMealsDto.toDomain() = BrowseMeal(
    id = id,
    name = name,
    calories = calories,
    user = user.toDomain(),
)
