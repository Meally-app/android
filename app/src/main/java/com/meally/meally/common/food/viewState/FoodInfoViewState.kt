package com.meally.meally.common.food.viewState

import com.meally.domain.food.Food

sealed interface FoodInfoViewState {
    data object Loading : FoodInfoViewState

    data class Loaded(
        val foodItem: FoodItemViewState,
    ) : FoodInfoViewState

    data object Error : FoodInfoViewState
}

data class FoodItemViewState(
    val name: String,
    val imageUrl: String?,
    val amount: Int,
    val calories: String,
    val carbs: String,
    val protein: String,
    val fat: String,
    val unitOfMeasurement: String,
)

fun Food.toViewState() =
    FoodItemViewState(
        name = name,
        imageUrl = imageUrl.ifEmpty { null },
        calories = calories.toString(),
        carbs = carbs.toString(),
        protein = protein.toString(),
        fat = fat.toString(),
        unitOfMeasurement = unitOfMeasurement.abbreviation,
        amount = 0,
    )
