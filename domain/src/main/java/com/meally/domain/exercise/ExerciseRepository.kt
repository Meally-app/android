package com.meally.domain.exercise

import com.meally.domain.common.util.Resource
import java.time.LocalDate

interface ExerciseRepository {

    suspend fun getExerciseForDate(date: LocalDate): Resource<ExerciseForDate>
}