package com.meally.data.exercise

import com.meally.data.diary.dto.toDomain
import com.meally.data.remote.network.api.MeallyAppApi
import com.meally.data.util.safeApiCall
import com.meally.domain.common.util.Resource
import com.meally.domain.common.util.map
import com.meally.domain.exercise.ExerciseForDate
import com.meally.domain.exercise.ExerciseRepository
import java.time.LocalDate

class ExerciseRepositoryImpl(
    private val meallyAppApi: MeallyAppApi,
): ExerciseRepository {
    override suspend fun getExerciseForDate(date: LocalDate) = safeApiCall {
        meallyAppApi.exerciseForDate(date)
    }.map {
        ExerciseForDate(
            isAuthorized = it.isAuthorized,
            date = date,
            exercise = it.exercise.map { it.toDomain() },
        )
    }
}