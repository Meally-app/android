package com.meally.data.diary.dto

import com.meally.domain.exercise.ExerciseActivity
import kotlinx.serialization.Serializable

@Serializable
data class ExerciseActivityDto(
    val name: String,
    val id: String,
)

fun ExerciseActivityDto.toDomain() = ExerciseActivity(
    name = name,
    id = id,
)
