package com.meally.meally.screens.food

import retrofit2.http.GET
import retrofit2.http.Path

interface MeallyAppApi {
    @GET("product/{barcode}")
    suspend fun getProductDetails(
        @Path("barcode") barcode: String,
    ): OpenFoodFactsProductDto
}
