package com.meally.meally.screens.browseMeals.mapper

import com.meally.domain.meal.BrowseMeal
import com.meally.meally.screens.browseMeals.state.BrowseMealsSearchState
import com.meally.meally.screens.browseMeals.ui.model.BrowseMealsViewState

fun browseMealsMapper(isLoading: Boolean, meals: List<BrowseMeal>, searchState: BrowseMealsSearchState) = BrowseMealsViewState(
    isLoading = isLoading,
    meals = meals,
    searchState = searchState,
)