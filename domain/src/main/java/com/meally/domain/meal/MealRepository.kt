package com.meally.domain.meal

import com.meally.domain.common.util.Resource

interface MealRepository {

    suspend fun browseMeals(
        searchQuery: String,
        caloriesMin: Double,
        caloriesMax: Double,
        showOnlyLiked: Boolean,
    ): Resource<List<BrowseMeal>>

    suspend fun myMeals(): Resource<List<Meal>>

    suspend fun loadMeal(
        mealId: String,
    ) : Resource<Meal>

    suspend fun createMeal(
        name: String,
        visibility: MealVisibility,
        foodInMeal: List<FoodInMeal>,
    ): Resource<Meal>

    suspend fun editMeal(
        meal: Meal
    ): Resource<Meal>

    suspend fun deleteMeal(
        meal: Meal
    ): Resource<Unit>

    suspend fun likeMeal(
        meal: Meal
    ): Resource<Unit>
}