package com.meally.data.remote.network.api

import com.meally.data.food.dto.FoodDto
import com.meally.data.user.dto.UserDto
import com.meally.data.util.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MeallyAppApi {
    /*
     *   /test
     */

    @GET("/public/test")
    suspend fun publicTest(): ApiResponse<String>

    @GET("/test")
    suspend fun test(): ApiResponse<String>

    /*
     *   /food
     */

    @GET("/public/food/{barcode}")
    suspend fun getFood(
        @Path("barcode") barcode: String,
    ): FoodDto

    /*
     *   /users
     */

    @GET("/users/me")
    suspend fun me(): UserDto
}
