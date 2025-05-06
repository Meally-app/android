package com.meally.data.remote.network.api

import com.meally.data.diary.dto.DiaryForDateResponseDto
import com.meally.data.diary.dto.FoodEntryInsertDto
import com.meally.data.food.dto.FoodDto
import com.meally.data.user.dto.UserDto
import com.meally.data.util.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate

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

    /*
     *   /diary
     */
    @GET("/diary")
    suspend fun diary(@Query("date") date: LocalDate): List<DiaryForDateResponseDto>

    @POST("/food-entry")
    suspend fun foodEntry(@Body request: FoodEntryInsertDto)
}
