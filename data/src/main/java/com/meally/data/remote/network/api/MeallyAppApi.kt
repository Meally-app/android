package com.meally.data.remote.network.api

import com.meally.data.diary.dto.DiaryForDateResponseDto
import com.meally.data.diary.dto.FoodEntryDto
import com.meally.data.diary.dto.DiarySummaryDayDto
import com.meally.data.diary.dto.FoodEntryInsertDto
import com.meally.data.exercise.dto.ExerciseForDateResponseDto
import com.meally.data.food.dto.FoodDto
import com.meally.data.mealType.dto.MealTypeDto
import com.meally.data.user.dto.UserDto
import com.meally.data.util.ApiResponse
import com.meally.data.weight.dto.WeightDto
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

    @GET("/public/food/search")
    suspend fun searchFood(
        @Query("query") query: String,
    ): List<FoodDto>

    @GET("/food/recent")
    suspend fun recentFood() : List<FoodDto>

    /*
     *   /users
     */

    @GET("/users/me")
    suspend fun me(): UserDto

    /*
     *   /diary
     */

    @GET("/diary")
    suspend fun diary(@Query("date") date: LocalDate): DiaryForDateResponseDto

    @GET("/diary/summary")
    suspend fun diarySummary(
        @Query("from") from: LocalDate,
        @Query("to") to: LocalDate,
    ): List<DiarySummaryDayDto>

    @POST("/food-entry")
    suspend fun foodEntry(@Body request: FoodEntryInsertDto)

    /*
     *   /meal-type
     */

    @GET("/public/meal-type")
    suspend fun getMealTypes(): List<MealTypeDto>

    /*
     *   /weight
     */

    @POST("/weight")
    suspend fun insertWeight(@Body weightDto: WeightDto): WeightDto

    @GET("/weight")
    suspend fun getWeight(@Query("from") from: LocalDate, @Query("to") to: LocalDate): List<WeightDto>

    /*
     *   /strava
     */

    @POST("/strava/token")
    suspend fun sendStravaCode(@Query("code") code: String)

    @GET("/strava/sync")
    suspend fun syncStrava(@Query("date") date: LocalDate)

    /*
     *   /exercise
     */

    @GET("/exercise")
    suspend fun exerciseForDate(@Query("date") date: LocalDate): ExerciseForDateResponseDto
}
