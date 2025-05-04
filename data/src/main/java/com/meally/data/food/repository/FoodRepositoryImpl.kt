package com.meally.data.food.repository

import com.meally.data.food.dto.toDomain
import com.meally.data.remote.network.api.MeallyAppApi
import com.meally.data.util.safeApiCall
import com.meally.domain.barcode.Barcode
import com.meally.domain.common.util.Resource
import com.meally.domain.common.util.map
import com.meally.domain.food.Food
import com.meally.domain.food.FoodRepository

class FoodRepositoryImpl(
    private val meallyAppApi: MeallyAppApi,
) : FoodRepository {
    override suspend fun getFood(barcode: Barcode): Resource<Food> =
        safeApiCall {
            meallyAppApi.getFood(barcode.payload)
        }.map {
            it.toDomain()
        }
}
