package com.meally.meally.screens.foodInfo.ui.model

import com.meally.domain.mealType.MealType
import com.meally.meally.common.food.viewState.FoodInfoViewState

data class FoodEntryViewState(
    val foodInfoViewState: FoodInfoViewState,
    val mealTypeOptions: Map<String, MealType>,
)
