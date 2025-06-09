package com.meally.meally.screens.browseMeals.ui.model

import com.meally.domain.meal.BrowseMeal
import com.meally.meally.screens.browseMeals.state.BrowseMealsSearchState

data class BrowseMealsViewState(
    val isLoading: Boolean,
    val meals: List<BrowseMeal>,
    val searchState: BrowseMealsSearchState,
)
