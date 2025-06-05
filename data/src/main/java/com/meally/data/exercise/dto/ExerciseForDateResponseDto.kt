package com.meally.data.exercise.dto

import com.meally.data.diary.dto.ExerciseEntryDto

data class ExerciseForDateResponseDto(
    val exercise: List<ExerciseEntryDto>,
    val isAuthorized: Boolean,
)
