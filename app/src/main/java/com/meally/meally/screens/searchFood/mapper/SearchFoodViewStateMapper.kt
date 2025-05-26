package com.meally.meally.screens.searchFood.mapper

import com.meally.domain.food.Food
import com.meally.meally.screens.searchFood.ui.model.SearchFoodItem
import com.meally.meally.screens.searchFood.ui.model.SearchFoodViewState
import java.text.NumberFormat

fun searchFoodViewStateMapper(food: List<Food>, isLoading: Boolean) = SearchFoodViewState(
    isLoading = isLoading,
    food = food.map { it.toSearchFoodItem() }
)

fun Food.toSearchFoodItem() = SearchFoodItem(
    name = name,
    barcode = barcode,
    calories = formatter.format(calories),
)

private val formatter = NumberFormat.getInstance().apply {
    minimumFractionDigits = 0
    maximumFractionDigits = 0
}