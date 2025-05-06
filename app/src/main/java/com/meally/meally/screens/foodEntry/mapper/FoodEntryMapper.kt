package com.meally.meally.screens.foodEntry.mapper

import com.meally.domain.common.util.Resource
import com.meally.domain.food.Food
import com.meally.meally.screens.foodInfo.ui.model.FoodInfoViewState
import com.meally.meally.screens.foodInfo.ui.model.FoodItemViewState
import java.text.NumberFormat

private val formatter =
    NumberFormat.getInstance().apply {
        minimumFractionDigits = 1
        maximumFractionDigits = 1
    }

fun foodEntryMapper(
    food: Resource<Food>,
    amount: Int,
    isLoading: Boolean,
): FoodInfoViewState {
    if (isLoading) {
        return FoodInfoViewState.Loading
    }

    return when (food) {
        is Resource.Failure -> FoodInfoViewState.Error
        is Resource.Success -> {
            FoodInfoViewState.Loaded(
                with(food.data) {
                    FoodItemViewState(
                        name = name.ifEmpty { "Food" },
                        imageUrl = imageUrl.ifEmpty { null },
                        calories = format(calories, amount),
                        carbs = format(carbs, amount),
                        protein = format(protein, amount),
                        fat = format(fat, amount),
                        unitOfMeasurement = unitOfMeasurement.abbreviation,
                    )
                },
            )
        }
    }
}

private fun format(
    value: Double,
    amount: Int,
) = formatter.format(value * amount / 100.0)
