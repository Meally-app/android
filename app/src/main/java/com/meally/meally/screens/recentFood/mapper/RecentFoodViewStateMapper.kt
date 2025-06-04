package com.meally.meally.screens.recentFood.mapper

import com.meally.domain.food.Food
import com.meally.meally.screens.recentFood.ui.model.RecentFoodItem
import com.meally.meally.screens.recentFood.ui.model.RecentFoodViewState

fun recentFoodViewStateMapper(food: List<Food>, isLoading: Boolean) = RecentFoodViewState(
    isLoading = isLoading,
    items = food.map { it.toRecentFoodItem() }
)

fun Food.toRecentFoodItem() = RecentFoodItem(
    name = name,
    barcode = barcode,
    date = ""
)
