package com.meally.domain.exercise

import com.meally.domain.diary.ExerciseEntry
import java.time.LocalDate

data class ExerciseForDate(
    val isAuthorized: Boolean,
    val date: LocalDate,
    val exercise: List<ExerciseEntry>,
)
