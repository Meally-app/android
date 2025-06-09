package com.meally.data.meals.repository

import com.meally.data.meals.dto.InsertMealDto
import com.meally.data.meals.dto.toDomain
import com.meally.data.meals.dto.toDto
import com.meally.data.remote.network.api.MeallyAppApi
import com.meally.data.util.safeApiCall
import com.meally.domain.common.util.Resource
import com.meally.domain.meal.FoodInMeal
import com.meally.domain.meal.Meal
import com.meally.domain.meal.MealRepository
import com.meally.domain.meal.MealVisibility

class MealRepositoryImpl(
    private val meallyAppApi: MeallyAppApi
) : MealRepository {
    override suspend fun browseMeals(searchQuery: String, caloriesMin: Double, caloriesMax: Double) = safeApiCall {
        meallyAppApi.browseMeals(
            query = searchQuery,
            caloriesMin = caloriesMin,
            caloriesMax = caloriesMax,
        ).map { it.toDomain() }
    }

    override suspend fun myMeals(): Resource<List<Meal>> = safeApiCall {
        meallyAppApi.myMeals().map { it.toDomain() }
    }

    override suspend fun loadMeal(mealId: String) = safeApiCall {
        meallyAppApi.getMealById(mealId).toDomain()
    }

    override suspend fun createMeal(name: String, visibility: MealVisibility, foodInMeal: List<FoodInMeal>) = safeApiCall {
        meallyAppApi.insertMeal(
            InsertMealDto(
                name = name,
                visibility = visibility.name.uppercase(),
                foodInMeal = foodInMeal.map { it.toDto() },
            )
        ).toDomain()
    }

    override suspend fun editMeal(meal: Meal) = safeApiCall {
        meallyAppApi.insertMeal(
            InsertMealDto(
                mealId = meal.id,
                name = meal.name,
                visibility = meal.visibility.name.uppercase(),
                foodInMeal = meal.foodInMeal.map { it.toDto() }
            )
        ).toDomain()
    }

    override suspend fun deleteMeal(meal: Meal) = safeApiCall<Unit> {
        meallyAppApi.deleteMeal(meal.id)
    }
}