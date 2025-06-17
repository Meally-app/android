package com.meally.meally.screens.foodEntry.mapper

import com.meally.domain.common.util.Resource
import com.meally.domain.food.Food
import com.meally.domain.mealType.MealType
import com.meally.meally.common.food.viewState.FoodInfoViewState
import com.meally.meally.common.food.viewState.FoodItemViewState
import com.meally.meally.screens.foodEntry.ui.model.FoodEntryViewState
import java.text.NumberFormat
import java.time.LocalDate

private val formatter =
    NumberFormat.getInstance().apply {
        minimumFractionDigits = 1
        maximumFractionDigits = 1
    }

fun foodEntryMapper(
    food: Resource<Food>,
    amount: Int,
    isLoading: Boolean,
    mealTypes: List<MealType>,
    selectedDate: LocalDate,
    isManualEntry: Boolean,
): FoodEntryViewState = FoodEntryViewState(
    foodInfoViewState = mapFoodInfo(food, isLoading, amount, isManualEntry),
    mealTypeOptions = mapMealTypes(mealTypes),
    selectedDate = selectedDate,
    isManualEntry = isManualEntry,
)

private fun mapMealTypes(mealTypes: List<MealType>) = mealTypes.associateBy { it.name.replaceFirstChar { it.uppercase() } }

private fun mapFoodInfo(
    food: Resource<Food>,
    isLoading: Boolean,
    amount: Int,
    isManualEntry: Boolean,
): FoodInfoViewState {
    if (isLoading) {
        return FoodInfoViewState.Loading
    }

    if (isManualEntry) {
        return FoodInfoViewState.Loaded(
            FoodItemViewState(
                name = "Manual entry",
                imageUrl = null,
                calories = format(100.0, amount),
                carbs = "0",
                fat = "0",
                protein = "0",
                unitOfMeasurement = "",
                amount = amount,
            )
        )
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
                        amount = amount,
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
