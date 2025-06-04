package com.meally.domain.diary

import com.meally.domain.exercise.ExerciseActivity
import java.time.LocalDate

data class ExerciseEntry(
    val name: String,
    val calories: Double,
    val timeSpentInSeconds: Long,
    val activity: ExerciseActivity,
    val date: LocalDate,
)
